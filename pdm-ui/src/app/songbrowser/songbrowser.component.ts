import { Component, OnInit } from '@angular/core';
import { SongService } from '../song.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionService } from '../collection.service';
import { Collection } from '../Collection';
import { SongInView } from '../SongInView';

@Component({
  selector: 'app-songbrowser',
  templateUrl: './songbrowser.component.html',
  styleUrls: ['./songbrowser.component.css']
})
export class SongbrowserComponent implements OnInit {
  songList: SongInView[] = []
  runtimeInfo: {[key:number]:number} = {}
  userID: number = 0
  collectionList: Collection[] = []
  selected: string = ""
  constructor(private songService : SongService, private collectionService : CollectionService,
              private router : Router, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
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

  goBack(): void {
    this.router.navigate(['/users/'+this.userID+'/home'])
  }
}
