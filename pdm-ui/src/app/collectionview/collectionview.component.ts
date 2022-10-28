import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { CollectionService } from '../collection.service'
import { UtilsService } from '../utils.service'
import { Collection } from '../Collection'
import { Observable } from 'rxjs'
import * as stream from "stream";

@Component({
  selector: 'app-collectionview',
  templateUrl: './collectionview.component.html',
  styleUrls: ['./collectionview.component.css']
})
export class CollectionviewComponent implements OnInit {
  userID: number = 0
  songInfo: {[key:number]:number} = {} // the song count of a collection [id, count]
  durationInfo: {[key:number]:number} = {}
  collectionList: Collection[] = []
  songCount: number = 0
  duration: number = 0
  constructor(private router : Router, private collectionService : CollectionService,
              private utilsService : UtilsService, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
    })
  }

  ngOnInit(): void {
    this.setCollections()
  }

  OnClick(collectionID: number): void {
    this.router.navigate(['/users/' + this.userID + '/collections/' + collectionID])
  }

  goBack(): void {
    this.router.navigate(['/users/'+this.userID+'/home'])
  }

  CreateCollection(title: string): void {
    if (title != "") {
      title = title.replace(' ', '-')
      var newCollection: Collection = {title: title, collectionID: 0}
      this.collectionService.createCollection(newCollection).subscribe(returnValue => {
        this.collectionService.createUserCollectionRelationship(this.userID, returnValue).subscribe(result => {
          this.setCollections()
        })
      })
    }
  }

  setCollections(): void {
    this.collectionService.getUserCollections(this.userID).subscribe(collectionList => {
      this.collectionList = collectionList
      collectionList.forEach(collection => {
        this.collectionService.getSongCount(collection.collectionID).subscribe(resultNum => {
          this.songInfo[collection.collectionID] = resultNum
        })
        this.collectionService.getDuration(collection.collectionID).subscribe(resultNum => {
          this.durationInfo[collection.collectionID] = parseFloat((resultNum / 60).toFixed(2))
        })
      })
    })
  }

  //we aren't using these
  getSongCount(title: string): void {
    var collectionID: number
    this.collectionService.getCollectionByName(this.userID, title)
      .subscribe(returnCollection => {
        this.collectionService.getSongCount(returnCollection.collectionID)
          .subscribe(songCount => this.songCount = songCount)
      })
  }

  //we aren't using this rn
  getDuration(title: string): void {
    var collectionID: number
    console.log("TEST TEST")
    this.collectionService.getCollectionByName(this.userID, title)
      .subscribe(returnCollection => {
        this.collectionService.getDuration(returnCollection.collectionID)
          .subscribe(duration => this.duration = duration / 60) // duration must be in MINUTES
      })
  }
}
