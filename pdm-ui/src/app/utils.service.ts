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

  private songInfo: {[key:number]:number;} = {};

  private collectionList: Collection[] = []

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

  public setSongInfo(songInfoToStore: {[key:number]:number;}) {
    this.songInfo = songInfoToStore
  }

  public getSongInfo(): {[key:number]:number;} {
    return this.songInfo
  }

  public setCollection(collection: Collection[]): void {
    this.collectionList = collection
  }

  public getCollection(): Collection[] {
    return this.collectionList
  }

}
