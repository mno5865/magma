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
    this.loginService.verifyUser(username).subscribe(userInfo => {
      this.userInfo = userInfo
      if (password == this.userInfo.password) {
        this.utilsService.setUser(this.userInfo)
        this.router.navigate(['/users/'+this.userInfo.userID+'/home'])
      } else {
        this.userInfo = {userID: -1, username: "", password: "", email: "", firstName: "", lastName: "",
          creationDate: new Date, accessDate: new Date}
      }
    })
  }

  goToCreate(): void {
    this.router.navigate(['/', 'create'])
  }
}
