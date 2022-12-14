/// file/component: Collection View
/// description: Displays a list of collections a user has made
/// author: Adrian Burgos - awb8593

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
          this.durationInfo[collection.collectionID] = resultNum
        })
      })
    })
  }

  convertRuntime(runtime: number): string {
    if (runtime % 60 < 10) {
      return Math.floor(runtime/60).toString() + ":0" + runtime % 60
    }
    return Math.floor(runtime/60).toString() + ":" + runtime % 60
  }
}
