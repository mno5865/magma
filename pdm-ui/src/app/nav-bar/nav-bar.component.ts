import { Component, OnInit } from '@angular/core'
import {ActivatedRoute, Router} from "@angular/router"
import { faFire } from '@fortawesome/free-solid-svg-icons'
import {User} from "../User";
import {UtilsService} from "../utils.service";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {
  faFire = faFire
  userID: number = 0
  userInfo: User = {userID: -1, username: "", password: "admin", email: "", firstName: "", lastName: "",
    creationDate: new Date, accessDate: new Date}


  constructor(private router : Router, route : ActivatedRoute, private utilsService : UtilsService) {

  }


  ngOnInit(): void {
  }

  idCheck(): void {
    this.userInfo = this.utilsService.getUser()
    this.userID=this.userInfo.userID
  }

  goHome(): void {
    this.idCheck()
    this.router.navigate(['/users/' + this.userID + '/home']).then()
  }

  goToCollections(): void {
    this.idCheck()
    this.router.navigate(['/users/' + this.userID + '/collections']).then()
  }

  goToSongBrowser(): void {
    this.idCheck()
    this.router.navigate(['/users/' + this.userID + '/songs']).then()
  }

  goToFriends(): void {
    this.idCheck()
    this.router.navigate(['/users/' + this.userID + '/friends']).then()
  }

  goToProfile(): void {
    this.idCheck()
    this.router.navigate(['users/' + this.userID + '/profile'])
  }

  logout(): void {
    this.idCheck()
    this.router.navigate(['/login'])
  }
}

