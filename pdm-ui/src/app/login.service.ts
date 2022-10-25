import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './User';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private userURL = 'https://localhost:8080/users';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  verifyUser(username: string): Observable<User> {
    console.log(this.http.get<User>(this.userURL+"/"+username, this.httpOptions))
    return this.http.get<User>(this.userURL+"/"+username, this.httpOptions)
  }
}
