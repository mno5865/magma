import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'
import {LoginService} from "../login.service";
import {User} from "../User";
import {UtilsService} from "../utils.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
  private userID: number = 0;
  userInfo: User = {userID: -1, username: "", password: "admin", email: "", firstName: "", lastName: "",
    creationDate: new Date, accessDate: new Date}
  followersCount: number = 0;
  followingCount: number = 0;
  collectionCount: number = 0;


  constructor(private router : Router, route : ActivatedRoute, private utilService : UtilsService) {

    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
      this.userInfo.firstName = utilService.getFirstName();
      this.followersCount = 0;
      this.followingCount = 0;
    })
  }


  ngOnInit(): void {
  }

  goToCollections(): void {
    this.router.navigate(['/users/' + this.userID + '/collections'])
  }

  goBack(): void {
    this.router.navigate(['/users/' + this.userID + '/home'])
  }
}
