import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './User';
import { Collection } from './Collection';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private userURL = 'http://localhost:8080/api/users';
  private collectionURL = 'http://localhost:8080/api/collections';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  verifyUser(username: string): Observable<User> {
    return this.http.get<User>(this.userURL+"/"+username, this.httpOptions)
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.userURL, JSON.stringify(user), this.httpOptions)
  }

  createCollection(collection: Collection): Observable<Collection> {
    return this.http.post<Collection>(this.collectionURL, JSON.stringify(collection), this.httpOptions)
  }
}
