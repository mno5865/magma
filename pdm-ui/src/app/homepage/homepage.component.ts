/// file/component: Home Page
/// description: A home page for users to access different parts of the site
/// author: Adrian Burgos - awb8593, Mildness Onyekwere - mno5865

import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { CollectionService } from '../collection.service'
import { UtilsService } from '../utils.service'
import { User } from '../User'
import { faFire } from '@fortawesome/free-solid-svg-icons';
import { Observable } from 'rxjs'
import * as stream from "stream";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  currentUser: User = {userID: 0, username: "", password: "", firstName: "", lastName: "", creationDate: new Date(),
    accessDate: new Date(), email: ""}

  constructor(private router : Router, private utilsService: UtilsService, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.currentUser.firstName = this.utilsService.getFirstName()
    })
  }

  ngOnInit(): void {

  }


}
