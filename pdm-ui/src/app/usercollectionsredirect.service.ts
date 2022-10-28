import { Injectable } from '@angular/core'
import { CanActivate } from '@angular/router'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { User } from './User'
import { Observable, of } from 'rxjs'
import { UtilsService } from './utils.service'
import { Router } from '@angular/router'

/// this service redirects to the user's specific CollectionView (the list of all their collections)
@Injectable({
  providedIn: 'root'
})
export class UsercollectionsredirectService implements CanActivate {

  constructor (private router: Router, private utilsService : UtilsService) { }

  canActivate(): Observable<boolean> {
    var userID: number = this.utilsService.getUserID();
    this.router.navigate(["users/" + userID + "/collections"])
    return of(false)
  }

}
