import { Injectable } from '@angular/core'
import { Collection } from './Collection'
import { Observable } from 'rxjs'
import { User } from './User'
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {
  private user : User = {userID: 0, username: "", password: "", creationDate: new Date(), accessDate: new Date(),
    email: "", lastName: "", firstName: ""}

  private songInfo: {[key:number]:number;} = {};

  private collectionList: Collection[] = []

  private userURL = 'http://localhost:8080/api/users';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  public getUsername(): string {
    return this.user.username;
  }

  public getFirstName(): string {
    return this.user.firstName
  }

  public getUserID(): number {
    return this.user.userID;
  }

  public getUserByEmail(email: string): Observable<User> {
    return this.http.get<User>(this.userURL+"/email/"+email, this.httpOptions)
  }

  public getFriends(userID: number): Observable<User[]> {
    return this.http.get<User[]>(this.userURL+"/"+userID+"/following")
  }

  public unfollowFriend(userID: number, friendID: number): Observable<number> {
    return this.http.delete<number>(this.userURL+"/"+userID+"/following/"+friendID, this.httpOptions)
  }

  public followFriend(userID: number, friendID: number): Observable<number> {
    return this.http.post<number>(this.userURL+"/"+userID+"/following/"+friendID, this.httpOptions)
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
