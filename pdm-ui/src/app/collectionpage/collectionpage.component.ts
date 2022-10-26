import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';
import { Collection } from '../Collection';

@Component({
  selector: 'app-collectionpage',
  templateUrl: './collectionpage.component.html',
  styleUrls: ['./collectionpage.component.css']
})
export class CollectionpageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
