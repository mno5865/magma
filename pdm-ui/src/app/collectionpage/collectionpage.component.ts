import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CollectionService } from '../collection.service';
import { Collection } from '../Collection';

@Component({
  selector: 'app-collectionpage',
  templateUrl: './collectionpage.component.html',
  styleUrls: ['./collectionpage.component.css']
})
export class CollectionpageComponent implements OnInit {

  collectionInfo: Collection = {collectionID: -1, title: ""}
  constructor(private router : Router, private collectionService : CollectionService) { }

  ngOnInit(): void {
  }



}
