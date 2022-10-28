/// file/component: Home Redirect Service
/// description: Redirects users to the correct page
/// author: Gregory Ojiem - gro3228, Adrian Burgos - awb8593

import { Injectable } from '@angular/core'
import { CanActivate } from '@angular/router'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { User } from './User'
import { Observable, of } from 'rxjs'
import { Router } from '@angular/router'
import { UtilsService } from './utils.service'

@Injectable({
  providedIn: 'root'
})
export class HomeredirectService implements CanActivate{

  constructor(private router: Router, private utilsService : UtilsService) { }

  canActivate(): Observable<boolean> {
    var userID: number = this.utilsService.getUserID();
    this.router.navigate(["users/" + userID + "/home"])
    return of(false)
  }
}
