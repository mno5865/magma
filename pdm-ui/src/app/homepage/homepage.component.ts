/// file/component: Home Page
/// description: A home page for users to access different parts of the site
/// author: Adrian Burgos - awb8593

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
  currentUser: User = {userID: 0, username: "", password: "", firstName: "", lastName: "", creationDate: new Date(),
    accessDate: new Date(), email: ""}

  constructor(private router : Router, private utilsService: UtilsService, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
      this.currentUser.firstName = this.utilsService.getFirstName()
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
    this.router.navigate(['/users/' + this.userID + '/songs'])
  }

  logout(): void {
    this.router.navigate(['/login'])
  }

}
