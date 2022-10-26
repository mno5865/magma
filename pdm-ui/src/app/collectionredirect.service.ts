import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './User';
import { Collection } from './Collection'
import { Observable, of } from 'rxjs';
import { CollectionService } from './collection.service'
import { Router } from '@angular/router';

// This service redirects to a specified Collection owned by the User
@Injectable({
  providedIn: 'root'
})
export class CollectionredirectService implements CanActivate{

  constructor(private router: Router, private collectionService : CollectionService) { }

  canActivate(): Observable<boolean> {
    var collectionID: number = this.collectionService.getCollectionID();
    this.router.navigate(["collections/" + collectionID])
    return of(false)
  }
}
