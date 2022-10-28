import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Collection } from './Collection';
import {map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CollectionService {
  private globalURL = 'http://localhost:8080/api/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private collectionID = 0
  private collectionName = "_"

  constructor(private http: HttpClient) { }

  public getCollectionID(): number {
    return this.collectionID
  }

  public getCollectionName(): string {
    return this.collectionName
  }

  public getSongCount(collectionID: number): Observable<number> {
    console.log("THE SONG COUNT IS: " + this.http.get<number>(this.globalURL+"collections/"+collectionID+"/song_count"))
    return this.http.get<number>(this.globalURL+"collections/"+collectionID+"/song_count", this.httpOptions)
  }

  public getCollectionByName(userID: number, collectionName: string): Observable<Collection> {
    return this.http.get<Collection>(this.globalURL+"users/"+userID+"/collections/"+collectionName, this.httpOptions)
  }

  public getUserCollections(userID: number): Observable<Collection[]> {
    var collections = this.http.get<Collection[]>(this.globalURL+"users/"+userID+"/collections", this.httpOptions)
    return collections
  }

  createCollection(collection: Collection): Observable<number> {
    return this.http.post<number>(this.globalURL+"collections", JSON.stringify(collection), this.httpOptions)
  }

  createUserCollectionRelationship(userID: number, collectionToMakeID: number): Observable<number> {
    console.log("THE COMMAND IS:" + this.globalURL+"users/"+userID+"/collections/"+collectionToMakeID)
    return this.http.post<number>(this.globalURL+"users/"+userID+"/collections/"+collectionToMakeID, this.httpOptions)
  }
}
