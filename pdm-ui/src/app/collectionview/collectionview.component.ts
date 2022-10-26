import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionService } from '../collection.service';
import { Collection } from '../Collection';

@Component({
  selector: 'app-collectionview',
  templateUrl: './collectionview.component.html',
  styleUrls: ['./collectionview.component.css']
})
export class CollectionviewComponent implements OnInit {

  collectionInfo: Collection = {collectionID: -1, title: ""}
  constructor(router : Router, private collectionService : CollectionService, route: ActivatedRoute) {
    var username: string = ""
    route.params.subscribe((params) => {
      username = params["username"]   // this keeps track of the username field of the URL
    })
  }

  ngOnInit(): void {
  }

  CreateCollection(title: string): void {
    if (title != "") {
      var newCollection: Collection = {title: title, collectionID: 0}
      this.collectionService.createCollection(newCollection).subscribe();
    }
  }

  getCollections(): void {}
}
