import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { CollectionService } from '../collection.service'
import { Collection } from '../Collection'
import { Observable } from 'rxjs'

@Component({
  selector: 'app-collectionview',
  templateUrl: './collectionview.component.html',
  styleUrls: ['./collectionview.component.css']
})
export class CollectionviewComponent implements OnInit {
  userID: number = 0
  collectionInfo: Collection = {collectionID: -1, title: ""}
  constructor(router : Router, private collectionService : CollectionService, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
    })
  }

  ngOnInit(): void {
  }

  CreateCollection(title: string): void {
    if (title != "") {
      var newCollection: Collection = {title: title, collectionID: 0}
      this.collectionService.createCollection(newCollection).subscribe()
    }
  }

  getCollections(): Observable<Collection[]> {
    return this.collectionService.getUserCollections(this.userID)
  }
}
