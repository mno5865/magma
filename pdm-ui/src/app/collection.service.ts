import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Collection } from './Collection';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CollectionService {
  private collectionURL = 'http://localhost:8080/api/collections';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  createCollection(collection: Collection): Observable<Collection> {
    return this.http.post<Collection>(this.collectionURL, JSON.stringify(collection), this.httpOptions)
  }
}
