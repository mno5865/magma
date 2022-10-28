/// file/component: Album Service
/// description: Handles album related HTTP requests
/// author: Gregory Ojiem - gro3228, Adrian Burgos - awb8593

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Album } from './Album';
import {map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  private globalURL = 'http://localhost:8080/api/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  private albumID: number = 0
  private albumTitle: string = ""
  private release: Date = new Date()

  constructor(private http: HttpClient) { }

  public getAlbumID(): number {
    return this.albumID
  }

  public getAlbumTitle(): string {
    return this.albumTitle
  }

  public getRelease(): Date {
    return this.release
  }

  public getAlbumsFromCollection(collectionID: number): Observable<Album[]> {
    return this.http.get<Album[]>(this.globalURL+"collections/"+collectionID+"/albums", this.httpOptions)
  }

  listenToAlbum(userID: number, albumID: number): Observable<number> {
    return this.http.post<number>(this.globalURL+"users/"+userID+"/albums/"+albumID, this.httpOptions)
  }

  public getAlbumRuntime(albumID: number): Observable<number> {
    return this.http.get<number>(this.globalURL+"albums/"+albumID+"/total_duration", this.httpOptions)
  }

}
