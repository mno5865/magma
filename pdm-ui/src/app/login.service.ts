import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private userURL = 'http://localhost:8080/users';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  verifyUser(username: string, password: string): number {
    return this.http.put<Customer>(this.customerURL+"/"+username+"/"+id+"/-1", this.httpOptions)
  }

  constructor() { }
}
