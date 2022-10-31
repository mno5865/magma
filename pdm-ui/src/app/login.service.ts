/// file/component: Login Service
/// description: Handles user verification/creation related HTTP requests
/// author: Gregory Ojiem - gro3228, Adrian Burgos - awb8593

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './User';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private userURL = 'http://localhost:8080/api/users';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  loginUser(username: string): Observable<User> {
    return this.http.get<User>(this.userURL+"/"+username, this.httpOptions)
  }

  verifyUser(pass: string, hashedPass: string): Observable<Boolean> {
    return this.http.get<Boolean>(this.userURL+"/verify/"+hashedPass+"/"+pass, this.httpOptions)
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.userURL, JSON.stringify(user), this.httpOptions)
  }
}
