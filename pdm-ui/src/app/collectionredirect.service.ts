import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './User';
import { Observable, of } from 'rxjs';
import { UtilsService } from './utils.service'
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CollectionredirectService implements CanActivate{

  constructor(private router: Router, private utilsService : UtilsService) { }

  canActivate(): Observable<boolean> {
    var username: string = this.utilsService.getUsername();
    this.router.navigate(["collections/" + username])
    return of(false)
  }
}
