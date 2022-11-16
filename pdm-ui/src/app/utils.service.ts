/// file/component: Utils Service
/// description: Handles utility related HTTP requests
/// author: Gregory Ojiem - gro3228, Adrian Burgos - awb8593

import { Injectable } from '@angular/core'
import { Collection } from './Collection'
import { Observable } from 'rxjs'
import { User } from './User'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Artist} from "./Artist";

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

  public getUser(): User {
    return this.user
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

  public getCollectionCount(userID: number): Observable<number> {
    return this.http.get<number>(this.userURL+"/"+ userID +"/collections/count", this.httpOptions)
  }

  public getFollowersCount(userID: number): Observable<number> {
    return this.http.get<number>(this.userURL+"/"+ userID +"/followers/count", this.httpOptions)
  }

  public getFollowingCount(userID: number): Observable<number> {
    return this.http.get<number>(this.userURL+"/"+ userID +"/following/count", this.httpOptions)
  }

  public getTopTenArtists(userID: number): Observable<Artist[]> {
    return this.http.get<Artist[]>(this.userURL+"/"+ userID +"/top-ten-artists", this.httpOptions)
  }

  public getTopTenArtistsByPlays(userID: number): Observable<Artist[]> {
    return this.http.get<Artist[]>(this.userURL+"/"+ userID +"/top-ten-artists/by-plays", this.httpOptions)
  }

  public getTopTenArtistsByCollection(userID: number): Observable<Artist[]> {
    return this.http.get<Artist[]>(this.userURL+"/"+ userID +"/top-ten-artists/by-collections", this.httpOptions)
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
