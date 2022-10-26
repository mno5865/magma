import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionService } from '../collection.service';
import { Collection } from '../Collection';

@Component({
  selector: 'app-collectionpage',
  templateUrl: './collectionpage.component.html',
  styleUrls: ['./collectionpage.component.css']
})
export class CollectionpageComponent implements OnInit {

  collectionInfo: Collection = {collectionID: -1, title: ""}
  constructor(router : Router, private collectionService : CollectionService, route: ActivatedRoute) {
    var username: string = ""
    route.params.subscribe((params) => {
      username = params["username"]   // this keeps track of the username field of the URL
    })
  }

  ngOnInit(): void {
  }



}
