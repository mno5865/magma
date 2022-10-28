/// file/component: Friend View
/// description: Allows a user to follow new users and shows the user a list of people they follow
/// author: Gregory Ojiem - gro3228

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../User';
import { UtilsService } from '../utils.service';

@Component({
  selector: 'app-friendview',
  templateUrl: './friendview.component.html',
  styleUrls: ['./friendview.component.css']
})
export class FriendviewComponent implements OnInit {
  userID: number = 0
  friendList: User[] = []
  friendInfo: User = {userID: -1, username: "", password: "admin", email: "", firstName: "", lastName: "",
    creationDate: new Date, accessDate: new Date}

  constructor(private utilsService : UtilsService, private router : Router, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
    })
  }

  ngOnInit(): void {
    this.utilsService.getFriends(this.userID).subscribe(friends => {
      this.friendList = friends
    })
  }

  findFriendByEmail(email: string) {
    this.utilsService.getUserByEmail(email).subscribe(returnUser => {
      this.friendInfo = returnUser
      const followInfo = document.getElementsByClassName('follow')[0] as HTMLSelectElement;
      if (followInfo != null) {
        followInfo.style.display = 'block';
      }
    })
  }

  unfollowUser(userID: number, friendID: number) {
    this.utilsService.unfollowFriend(userID, friendID).subscribe(returnVal => {
      this.ngOnInit()
    })
  }

  followUser(userID: number, friendID: number) {
    this.utilsService.followFriend(userID, friendID).subscribe(returnVal => {
      this.ngOnInit()
      const followInfo = document.getElementsByClassName('follow')[0] as HTMLSelectElement;
      if (followInfo != null) {
        followInfo.style.display = 'none';
      }
    })
  }

  goBack(): void {
    this.router.navigate(['/users/'+this.userID+'/home'])
  }
}
