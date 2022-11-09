import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'
import { User } from "../User";
import { UtilsService } from "../utils.service";
import {faFire} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})

export class ProfileComponent implements OnInit {
  userID: number = 0;
  userInfo: User = {userID: -1, username: "", password: "admin", email: "", firstName: "", lastName: "",
    creationDate: new Date, accessDate: new Date}
  followersCount: number = 0;
  followingCount: number = 0;
  collectionCount: number = 0;

  faFire = faFire;


  constructor(private router : Router, route : ActivatedRoute, private utilService : UtilsService) {

    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
      this.userInfo.firstName = utilService.getFirstName();
      utilService.getCollectionCount(this.userID).subscribe(count => {
        this.collectionCount = count;
      })
      utilService.getFollowersCount(this.userID).subscribe(count => {
        this.followersCount = count;
      })
      utilService.getFollowingCount(this.userID).subscribe(count => {
        this.followingCount = count;
      })
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
