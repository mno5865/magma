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

}
