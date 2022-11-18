/// file/component: Song Browser
/// description: Allows a user to search for new songs and add them to collections
/// author: Gregory Ojiem - gro3228

import { Component, OnInit } from '@angular/core';
import { faPlay } from '@fortawesome/free-solid-svg-icons'
import { SongService } from '../song.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionService } from '../collection.service';
import { Collection } from '../Collection';
import { SongInView } from '../SongInView';
import { UtilsService } from "../utils.service";
import {Song} from "../Song";

@Component({
  selector: 'app-songbrowser',
  templateUrl: './songbrowser.component.html',
  styleUrls: ['./songbrowser.component.css']
})
export class SongbrowserComponent implements OnInit {
  faPlay = faPlay
  songList: SongInView[] = []
  recs: SongInView[] = []
  recsByGenre: Song[] = []
  recsByArtist: Song[] = []
  runtimeInfo: {[key:number]:number} = {}
  userID: number = 0
  collectionList: Collection[] = []
  selected: string = ""
  constructor(private songService : SongService, private collectionService : CollectionService,
              private utilsService: UtilsService, private router : Router, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
      this.utilsService.getSongRecommendationsByGenre(this.userID).subscribe(recs => {
        this.recsByGenre = recs
      })
      this.utilsService.getSongRecommendationsByArtist(this.userID).subscribe(recs => {
        this.recsByArtist= recs
      })
    })
  }

  ngOnInit(): void {
    this.setCollections()
  }

  convertRuntime(runtime: number): string {
    if (runtime % 60 < 10) {
      return Math.floor(runtime/60).toString() + ":0" + runtime % 60
    }
    return Math.floor(runtime/60).toString() + ":" + runtime % 60
  }

  searchForSongs(searchBy: string, searchTerm: string, sortBy: string, order: string) {
    this.songService.getOrderedSongs(searchBy, searchTerm, parseInt(sortBy) , order).subscribe(returnView => {
      console.log(returnView)
      this.songList = returnView
    })
  }

  onSelected(collection: string) {
    this.selected = collection
  }

  addSongToCollection(songID: number) {
    this.collectionService.addSongToCollection(parseInt(this.selected), songID).subscribe()
  }

  addAlbumToCollection(albumID: number) {
    this.collectionService.addAlbumToCollection(parseInt(this.selected), albumID).subscribe()
  }

  setCollections(): void {
    this.collectionService.getUserCollections(this.userID).subscribe(collectionList => {
      this.collectionList = collectionList
    })
  }

  listenToSong(songId: number): void {
    this.songService.listenToSong(this.userID, songId).subscribe()
  }

  setFilterByGenre(): void {

  }

  setFilterByArtist(): void {
  }
}
