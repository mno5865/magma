import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-friendview',
  templateUrl: './friendview.component.html',
  styleUrls: ['./friendview.component.css']
})
export class FriendviewComponent implements OnInit {
  userID: number = 0
  constructor(private router : Router, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
    })
  }

  ngOnInit(): void {
  }

  findFriendByEmail(email: string) {

  }
}
