import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { CollectionService } from '../collection.service'
import { UtilsService } from '../utils.service'
import { User } from '../User'
import { Observable } from 'rxjs'
import * as stream from "stream";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  userID: number = 0

  constructor(private router : Router, private utilsService: UtilsService, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
    })
  }

  ngOnInit(): void {

  }

  goToCollections(): void {
    this.router.navigate(['/users/' + this.userID + '/collections'])
  }

  goToFriends(): void {
    this.router.navigate(['/users/' + this.userID + '/friends'])
  }

  goToSongBrowser(): void {
    this.router.navigate(['/users/' + this.userID + '/songs/g'])
  }

  logout(): void {
    this.router.navigate(['/login'])
  }

}
