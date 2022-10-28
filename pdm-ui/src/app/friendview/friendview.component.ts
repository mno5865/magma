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
    })
  }

  unfollowUser(userID: number, friendID: number) {
    this.utilsService.unfollowFriend(userID, friendID).subscribe(returnVal => {
      this.ngOnInit()
    })
  }
}
