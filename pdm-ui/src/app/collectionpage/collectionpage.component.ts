import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { CollectionService } from '../collection.service'
import { UtilsService } from '../utils.service'
import { Collection } from '../Collection'
import { Observable } from 'rxjs'
import { Song } from '../Song'
import { SongService } from '../song.service'
import { Album } from '../Album'
import { AlbumService } from '../album.service'
import * as stream from "stream"

@Component({
  selector: 'app-collectionpage',
  templateUrl: './collectionpage.component.html',
  styleUrls: ['./collectionpage.component.css']
})
export class CollectionpageComponent implements OnInit {
  collectionID: number = 0
  userID: number = 0
  collectionTitle: string = "_"
  songCount: number = 0
  collectionList: Collection[] = []
  songList: Song[] = []
  albumList: Album[] = []
  durationInfo: {[key:number]:number} = {}

  constructor(private router : Router, private albumService : AlbumService, private songService : SongService,
              private collectionService : CollectionService,
              private utilsService : UtilsService, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]
      this.collectionID = params["collectionID"]   // this keeps track of the collectionID field of the URL
    })
  }

  ngOnInit(): void {
    this.collectionService.getCollectionByID(this.collectionID).subscribe(returnCollection => {
      this.collectionTitle = returnCollection.title
      this.collectionList.push(returnCollection)
      this.setSongs()
      this.setAlbums()
    })
  }

  setTitle(title: string): void {
    if (title != "") {
      this.collectionService.getCollectionByID(this.collectionID).subscribe(returnCollection => {
        returnCollection.title = title
        this.collectionTitle = title
        this.collectionService.updateCollection(returnCollection).subscribe()
      })
    }
  }

  setSongs(): void {
    this.songService.getSongsFromCollection(this.collectionID).subscribe(songList => {
      this.songList = songList // set the list of songs to display
      songList.forEach(song => {
        this.durationInfo[song.songId] = parseFloat((song.runtime / 60).toFixed(2))
      })
    })
  }

  setAlbums(): void {
    this.albumService.getAlbumsFromCollection(this.collectionID).subscribe(albumList => {
      this.albumList = albumList
      console.log(this.albumList)
    })
  }

  deleteSong(collectionID: number, songID: number): void {
    this.collectionService.deleteSongFromCollection(collectionID, songID).subscribe()
    this.redirectToViewSongs()
  }

  deleteAlbum(collectionID: number, albumID: number): void {
    console.log("I AM DELETING: " + albumID + " FROM: " + collectionID)
    this.collectionService.deleteAlbumFromCollection(collectionID, albumID).subscribe()
    this.redirectToViewSongs()
  }

  redirectToViewSongs() {
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router.navigate(['/users/' + this.userID + '/collections/' + this.collectionID])
    })
  }

  redirectToView() {
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router.navigate(['/users/' + this.userID + '/collections/'])
    })
  }

  DeleteCollection(collectionID: number): void {
    this.collectionService.deleteCollection(collectionID).subscribe()
    this.redirectToView()
  }

  listenToCollection(): void {
    this.collectionService.listenToCollection(this.userID, this.collectionID).subscribe()
  }

  listenToSong(songId: number): void {
    this.songService.listenToSong(this.userID, songId).subscribe()
  }

  listenToAlbum(albumID: number): void {
    console.log("PUSHING THE BUTTON ONE LAST TIME")
    this.albumService.listenToAlbum(this.userID, albumID).subscribe()
  }

  goBack(): void {
    this.router.navigate(['/users/'+this.userID+'/collections'])
  }

}
