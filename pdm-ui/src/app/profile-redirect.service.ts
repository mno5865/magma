import { Injectable } from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {UtilsService} from "./utils.service";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProfileRedirectService {
  constructor(private router: Router, private utilsService : UtilsService) { }

  canActivate(): Observable<boolean> {
    var userID: number = this.utilsService.getUserID();
    this.router.navigate(["users/" + userID + "/profile"])
    return of(false)
  }
}
