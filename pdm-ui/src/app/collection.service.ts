/// file/component: Collection Service
/// description: Handles collection related HTTP requests
/// author: Gregory Ojiem - gro3228, Adrian Burgos - awb8593

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
  }

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
    return this.http.get<number>(this.globalURL+"collections/"+collectionID+"/song_count", this.httpOptions)
  }

  public getDuration(collectionID: number): Observable<number> {
    return this.http.get<number>(this.globalURL+"collections/"+collectionID+"/total_duration", this.httpOptions)
  }

  public getCollectionByID(collectionID: number): Observable<Collection> {
    return this.http.get<Collection>(this.globalURL+"collections/"+collectionID, this.httpOptions)
  }

  public getCollectionByIDAndUserID(userID: number, collectionID: number): Observable<Collection> {
    return this.http.get<Collection>(this.globalURL+"users/"+userID+"/collections/"+collectionID, this.httpOptions)
  }

  public getUserCollections(userID: number): Observable<Collection[]> {
    var collections = this.http.get<Collection[]>(this.globalURL+"users/"+userID+"/collections", this.httpOptions)
    return collections
  }

  addSongToCollection(collectionID: number, songID: number): Observable<number> {
    console.log(this.globalURL+"collections/"+collectionID+"/songs/"+songID)
    return this.http.post<number>(this.globalURL+"collections/"+collectionID+"/songs/"+songID, this.httpOptions)
  }

  addAlbumToCollection(collectionID: number, albumID: number): Observable<number> {
    console.log(this.globalURL+"collections/"+collectionID+"/albums/"+albumID)
    return this.http.post<number>(this.globalURL+"collections/"+collectionID+"/albums/"+albumID, this.httpOptions)
  }

  deleteSongFromCollection(collectionID: number, songID: number): Observable<number> {
    console.log(this.http.delete<number>(this.globalURL+"collections/"+collectionID+"/songs/"+songID))
    return this.http.delete<number>(this.globalURL+"collections/"+collectionID+"/songs/"+songID, this.httpOptions)
  }

  deleteAlbumFromCollection(collectionID: number, albumID: number): Observable<number> {
    console.log(this.http.delete<number>(this.globalURL+"collections/"+collectionID+"/albums/"+albumID))
    return this.http.delete<number>(this.globalURL+"collections/"+collectionID+"/albums/"+albumID, this.httpOptions)
  }

  deleteCollection(collectionID: number): Observable<number> {
    return this.http.delete<number>(this.globalURL+"collections/"+collectionID+"/deleteAll", this.httpOptions)
  }

  createCollection(collection: Collection): Observable<number> {
    return this.http.post<number>(this.globalURL+"collections", JSON.stringify(collection), this.httpOptions)
  }

  createUserCollectionRelationship(userID: number, collectionToMakeID: number): Observable<number> {
    return this.http.post<number>(this.globalURL+"users/"+userID+"/collections/"+collectionToMakeID, this.httpOptions)
  }

  updateCollection(collection: Collection): Observable<Collection> {
    return this.http.put<Collection>(this.globalURL+"collections/"+collection.collectionID, JSON.stringify(collection),
      this.httpOptions)
  }

  listenToCollection(userID: number, collectionID: number): Observable<number> {
    return this.http.post<number>(this.globalURL+"users/"+userID+"/collections/listens/"+collectionID, this.httpOptions)
  }
}
