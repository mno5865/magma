/// file/component: Login
/// description: Allows a user to login to the site
/// author: Gregory Ojiem - gro3228

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';
import { UtilsService } from '../utils.service';
import { User } from '../User';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  //styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  userInfo: User = {userID: -1, username: "", password: "admin", email: "", firstName: "", lastName: "",
    creationDate: new Date, accessDate: new Date}

  constructor(private router : Router, private loginService : LoginService, private utilsService : UtilsService) { }

  ngOnInit(): void {
  }

  logInUser(username : string, password : string): void {
    this.loginService.loginUser(username).subscribe(userInfo => {
      this.userInfo = userInfo
      this.loginService.verifyUser(userInfo.userID, password, userInfo.password).subscribe(returnValue => {
        if (returnValue) {
          this.utilsService.setUser(this.userInfo)
          this.router.navigate(['/users/'+this.userInfo.userID+'/home'])
        } else {
          this.userInfo = {userID: -1, username: "", password: "", email: "", firstName: "", lastName: "",
            creationDate: new Date, accessDate: new Date}
        }
      })
    })
  }

  goToCreate(): void {
    this.router.navigate(['/', 'create'])
  }
}
