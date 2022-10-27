import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { CollectionService } from '../collection.service'
import { UtilsService } from '../utils.service'
import { Collection } from '../Collection'
import { Observable } from 'rxjs'
import { Song } from '../Song'
import { SongService } from '../song.service'
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
  durationInfo: {[key:number]:number} = {}

  constructor(private router : Router, private songService : SongService,  private collectionService : CollectionService,
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

  deleteSong(collectionID: number, songID: number): void {
    console.log("I AM DELETING: " + songID + " FROM: " + collectionID)
    this.collectionService.deleteSongFromCollection(collectionID, songID).subscribe()
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

}
