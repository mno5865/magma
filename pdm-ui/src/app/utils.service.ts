import { Injectable } from '@angular/core'
import { Collection } from './Collection'
import { Observable } from 'rxjs'
import { User } from './User'

@Injectable({
  providedIn: 'root'
})
export class UtilsService {
  private user : User = {userID: 0, username: "", password: "", creationDate: new Date(), accessDate: new Date(),
    email: "", lastName: "", firstName: ""}

  constructor() { }

  public getUsername(): string {
    return this.user.username;
  }

  public getUserID(): number {
    return this.user.userID;
  }

  public setUser(user: User): void {
    this.user = user
  }
}
