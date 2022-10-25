import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';
import { User } from '../User';

@Component({
  selector: 'app-createuser',
  templateUrl: './createuser.component.html',
  styleUrls: ['./createuser.component.css']
})
export class CreateuserComponent implements OnInit {
  userInfo: User = {userID: -1, username: "", password: "admin", email: "", firstName: "", lastName: "",
    creationDate: new Date, accessDate: new Date}

  constructor(private router : Router, private loginService : LoginService) { }

  ngOnInit(): void {
  }

  CreateAccount(username : string, password : string, email : string, firstName : string, lastName : string): void {
    var newUser: User = {username: username, password: password, email: email, firstName: firstName, lastName: lastName,
      creationDate: new Date(), accessDate: new Date(), userID: 0}
    this.loginService.createUser(newUser).subscribe()
    this.router.navigate(['/', 'login'])
  }

}
