import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
import cred
import time
import requests
import json
import os
import pathlib
import random

song_url = "http://localhost:8080/api/songs/"
genre_url = "http://localhost:8080/api/genres/"
album_url = "http://localhost:8080/api/albums/"
artist_url = "http://localhost:8080/api/artists/"
collection_url = "http://localhost:8080/api/collections/"
user_url = "http://localhost:8080/api/users/"

NUM_SONGS_TO_INITIALIZE_WITH = 1
NUMBER_OF_RECS_TO_ADD = 10
MAX_SONGS_PER_USER = 100
MAX_SONGS_PER_PLAYLIST = 10

def populate_song_data(username):
    #check if files exist
    local_path = pathlib.Path(__file__).parent.resolve()
    artist_exists = os.path.isfile(str(local_path) + "\\artist.txt")
    collection_exists = os.path.isfile(str(local_path) + "\\collection.txt")
    genre_exists = os.path.isfile(str(local_path) + "\\genre.txt")
    album_exists = os.path.isfile(str(local_path) + "\\album.txt")
    song_exists = os.path.isfile(str(local_path) + "\\song.txt")

    #create files if they don't exist
    if not artist_exists:
        with open('artist.txt', 'w') as f:
            pass
    if not collection_exists:
        with open('collection.txt', 'w') as f:
            pass
    if not genre_exists:
        with open('genre.txt', 'w') as f:
            pass
    if not album_exists:
        with open('album.txt', 'w') as f:
            pass
    if not song_exists:
        with open('song.txt', 'w') as f:
            pass

    #read in stored information on the artists and genres already created
    with open('artist.txt') as f:
        data = f.read()
        if data:
            artist_id_dict = json.loads(data)
        else:
            artist_id_dict = dict()
    with open('genre.txt') as f:
        data = f.read()
        if data:
            genre_id_dict = json.loads(data)
        else:
            genre_id_dict = dict()
    with open('collection.txt') as f:
        data = f.read()
        if data:
            collection_id_dict = json.loads(data)
        else:
            collection_id_dict = dict()
    with open('album.txt') as f:
        data = f.read()
        if data:
            album_id_dict = json.loads(data)
        else:
            album_id_dict = dict()
    with open('song.txt') as f:
        data = f.read()
        if data:
            song_id_dict = json.loads(data)
        else:
            song_id_dict = dict()

    def writeInfo():
        with open('artist.txt', 'w') as f:
            f.write(json.dumps(artist_id_dict))
        with open('genre.txt', 'w') as f:
            f.write(json.dumps(genre_id_dict))
        with open('collection.txt', 'w') as f:
            f.write(json.dumps(collection_id_dict))
        with open('album.txt', 'w') as f:
            f.write(json.dumps(album_id_dict))
        with open('song.txt', 'w') as f:
            f.write(json.dumps(song_id_dict))

    #set up spotify api connection
    sp = spotipy.Spotify(client_credentials_manager=SpotifyClientCredentials(client_id=cred.client_ID, client_secret=cred.client_SECRET))
    playlists = sp.user_playlists(username)['items']
    playlist_count = 1
    count_songs_global = 1
    count_songs = 1
    print("User: " + username + " Task: Iterate playlists\n")
    try:
        for x in playlists:
            if count_songs_global > MAX_SONGS_PER_USER:
                break
            time.sleep(0.01)
            if x['id'] in collection_id_dict:
                collection_id = collection_id_dict[x['id']]
            else:
                collection_id = add_collection(x['name'])
                collection_id_dict[x['id']] = collection_id
            playlist_songs_info = sp.playlist_items(x['id'])
            playlist_songs = playlist_songs_info['items']
            print("Playlist " + str(playlist_count) + ": " + x['name'])
            for y in playlist_songs:
                if count_songs > MAX_SONGS_PER_PLAYLIST:
                    break
                if y['track']['name'] is None:
                    count_songs += 1
                    print("\t\tinvalid song")
                    continue
                print("\tSong " + str(count_songs) + " is " + y['track']['name'])
                print("\t\t Artists: ", end="")
                number_of_artists_printed = 1
                artist_spotify_ids = []
                artist_names = []
                artist_ids = []
                for z in y['track']['artists']:
                    number_of_artists = len(y['track']['artists'])
                    print(z['name'], end="")
                    if (number_of_artists_printed < number_of_artists):
                        print(", ", end="")
                        number_of_artists_printed += 1
                    artist_spotify_ids.append(z['id'])
                    if z['id'] in artist_id_dict:
                        artist_ids.append(artist_id_dict[z['id']])
                    else:
                        artist_names.append((z['name'], z['id']))
                print("\n\t\t Genres: ", end="")
                genre_names = []
                for w in artist_spotify_ids:
                    time.sleep(0.01)
                    if w is None:
                        continue
                    artist = sp.artist(w)
                    genre_names += artist['genres']
                genre_names = [*set(genre_names)]
                print(", ".join(genre_names))
                print("\t\t Album: " + y['track']['album']['name'], end="")
                time.sleep(0.01)
                print()

                #all the above stuff was setting up variables/printing, here i'll actually add to database

                #add album
                if y['track']['album']['id'] in album_id_dict:
                    album_id = album_id_dict[y['track']['album']['id']]
                else:
                    album_id = add_album(y['track']['album']['name'], y['track']['album']['release_date'])
                    album_id_dict[y['track']['album']['id']] = album_id

                #adding a song
                if y['track']['id'] in song_id_dict:
                    song_id = song_id_dict[y['track']['id']]
                else:
                    song_id = add_song(y['track']['name'], y['track']['album']['release_date'], int(int(y['track']['duration_ms'])/1000))
                    song_id_dict[y['track']['id']] = song_id

                #adding genres
                genre_ids = []
                for a in genre_names:
                    if a in genre_id_dict:
                        genre_ids.append(genre_id_dict[a])
                    else:
                        new_genre_id = add_genre(a)
                        genre_ids.append(new_genre_id)
                        genre_id_dict[a] = new_genre_id

                #adding artists
                for b in artist_names:
                    new_artist_id = add_artist(b[0])
                    artist_ids.append(new_artist_id)
                    artist_id_dict[b[1]] = new_artist_id

                #creating relationships
                song_relationships(song_id, collection_id, genre_ids, artist_ids)

                #call an album function   REFACTOR TO INCLUDE MULTIPLE ALBUM CASES
                disc_number = y['track']['disc_number']
                album_relationships(album_id, song_id, disc_number, genre_ids, artist_ids)

                count_songs += 1
                count_songs_global += 1
            count_songs = 1
            playlist_count += 1
    except:
        writeInfo()
    writeInfo()
    print()

def add_collection(title):
    collection_obj = {"title": title}
    collection_id = int(requests.post(collection_url, json=collection_obj).content)
    #print(collection_obj)
    #print(collection_id)
    return collection_id

def add_artist(name):
    artist_obj = {"name": name}
    artist_id = int(requests.post(artist_url, json=artist_obj).content)
    #print(artist_obj)
    #print(artist_id)
    return artist_id

def add_album(title, releaseDate):
    album_obj = {"title": title, "releaseDate": releaseDate}
    album_id = int(requests.post(album_url, json=album_obj).content)
    #print(album_obj)
    #print(album_id)
    return album_id

def add_genre(name):
    genre_obj = {"name": name}
    genre_id = int(requests.post(genre_url, json=genre_obj).content)
    #print(genre_obj)
    #print(genre_id)
    return genre_id

def add_song(title, releaseDate, runtime):
    song_obj = {"title": title, "releaseDate": releaseDate, "runtime": runtime}
    song_id = int(requests.post(song_url, json=song_obj).content)
    #print(song_obj)
    #print(song_id)
    return song_id

def song_relationships(song_id, collection_id, genre_ids, artist_ids):
    #add song to the playlist its on - CollectionHoldsSong http://localhost:8080/api/collections/{col_id}/songs/{song_id}
    chs_statement = collection_url + str(collection_id) + "/songs/" + str(song_id)
    requests.post(chs_statement)

    # add song's genres - SongHasGenre http://localhost:8080/api/genres/8/songs/21
    for x in genre_ids:
        shg_statement = genre_url + str(x) + "/songs/" + str(song_id)
        requests.post(shg_statement)

    # add song's artists - ArtistReleasesSong http://localhost:8080/api/artists/1/songs/24
    for y in artist_ids:
        ars_statement = artist_url + str(y) + "/songs/" + str(song_id)
        requests.post(ars_statement)

def album_relationships(album_id, song_id, disc_number, genre_ids, artist_ids):
    pass
    #add the song being added onto the album - AlbumContainsSong http://localhost:8080/api/albums/{albumId}/songs/{songId}/{track}
    acs_statement = album_url + str(album_id) + "/songs/" + str(song_id) + "/" + str(disc_number)
    requests.post(acs_statement)

    # add the genre's of the album - AlbumHasGenre http://localhost:8080/api/genres/8/albums/2
    for x in genre_ids:
        ahg_statement = genre_url + str(x) + "/albums/" + str(album_id)
        requests.post(ahg_statement)

    # add the artist's of the album - ArtistReleasesAlbum http://localhost:8080/api/artists/{artistId}/albums/{albumId}
    for y in artist_ids:
        ara_statement = artist_url + str(y) + "/albums/" + str(album_id)
        requests.post(ara_statement)
        print()

def random_songs(amount):
    songs = []
    for x in range(0, amount):
        songs.append(json.loads(requests.get(song_url + "random").content))
    return songs

def random_albums(amount):
    albums = []
    for x in range(0, amount):
        albums.append(json.loads(requests.get(album_url + "random").content))
    return albums

def populate_user_info():
    # UserCreatesCollection
    # assign each user a collection sequentially until I run out, and then loop back around
    users = json.loads(requests.get(user_url).content)
    collections = json.loads(requests.get(collection_url).content)
    incrementor = 0
    while incrementor < len(collections):
        for x in users:
            if incrementor >= len(collections):
                break
            # assign each user a collection here http://localhost:8080/api/users/user_id/collections/collection_id
            ucc_statement = user_url + str(x['userID']) + "/collections/" + str(collections[incrementor]['collectionID'])
            time.sleep(0.01)
            requests.post(ucc_statement)
            incrementor += 1

    # adds random songs to each user http://localhost:8080/api/users/user_id/songs/song_id
    for x in users:
        songs = random_songs(NUM_SONGS_TO_INITIALIZE_WITH)
        for y in songs:
            ults_statement = user_url + str(x['userID']) + "/songs/" + str(y['songId'])
            time.sleep(0.01)
            requests.post(ults_statement)

    # 50% chance of adding a random album to each user for when I get those working http://localhost:8080/api/users/user_id/albums/album_id
    for x in users:
        add_album_bool = random.choice([0, 1])
        if add_album_bool:
            album = random_albums(1)[0]
            ulta_statement = user_url + str(x['userID']) + "/albums/" + str(album['albumID'])
            time.sleep(0.01)
            requests.post(ulta_statement)

    # add 10 songs from genre recommendation system http://localhost:8080/api/users/2/recommend/genre
    incrementor = 0
    for x in users:
        songs = json.loads(requests.get(user_url + str(x['userID']) + "/recommend/genre").content)
        songs = songs[:NUMBER_OF_RECS_TO_ADD]
        for y in songs:
            ults_statement = user_url + str(x['userID']) + "/songs/" + str(y['songId'])
            time.sleep(0.01)
            requests.post(ults_statement)

    # ideas
        # populate each user with songs from artist recommendation system
        # then 1-10 songs from genre recommendation system to see if artist recommendation has infelucne?
        # add random or non-random user follows user relations
        # randomized collection creating process, add in some of the albums to collections?

if __name__ == '__main__':
    # usernames
    # hk5j694obsw8mdnyx0pjvgxoa - saige
    # mpb316 - melissa
    # 22wvagtcv26x4wpjbqjvaqt3i - mildness
    # raynagii - ryan
    # awbuster02 - adrian
    usernames = ["hk5j694obsw8mdnyx0pjvgxoa", "22wvagtcv26x4wpjbqjvaqt3i", "raynagii", "mpb316"]
    for x in usernames:
        populate_song_data(x)
    populate_user_info()

