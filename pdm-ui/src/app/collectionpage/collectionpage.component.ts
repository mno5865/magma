import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { CollectionService } from '../collection.service'
import { UtilsService } from '../utils.service'
import { Collection } from '../Collection'
import { Observable } from 'rxjs'
import * as stream from "stream";

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

  constructor(router : Router, private collectionService : CollectionService, private utilsService : UtilsService, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]
      this.collectionID = params["collectionID"]   // this keeps track of the collectionID field of the URL
    })
  }

  ngOnInit(): void {
    console.log("TESTING 1 " + this.collectionID)
    this.collectionService.getCollectionByID(this.collectionID).subscribe(returnCollection => {
      console.log("TESTING 2")
      console.log("THE TITLE IS " + returnCollection.title)
      this.collectionTitle = returnCollection.title
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

}
