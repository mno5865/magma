/// file/component: Song Service
/// description: Handles song related HTTP requests
/// author: Gregory Ojiem - gro3228, Adrian Burgos - awb8593

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Song } from './Song';
import { SongInView } from './SongInView'
import {map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SongService {
  private globalURL = 'http://localhost:8080/api/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  private songID: number = 0
  private songTitle: string = ""
  private runtime: number = 0
  private release: Date = new Date()

  constructor(private http: HttpClient) { }

  public getSongID(): number {
    return this.songID;
  }

  public getSongTitle(): string {
    return this.songTitle;
  }

  public getRuntime(): number {
    return this.runtime;
  }

  public getRelease(): Date {
    return this.release;
  }

  public getSongsFromCollection(collectionID: number): Observable<Song[]> {
    return this.http.get<Song[]>(this.globalURL+"collections/"+collectionID+"/songs", this.httpOptions)
  }

  listenToSong(userID: number, songId: number): Observable<number> {
    return this.http.post<number>(this.globalURL+"users/"+userID+"/songs/"+songId, this.httpOptions)
  }

  getOrderedSongs(searchBy: string, searchTerm: string, sortBy: number, order: string): Observable<SongInView[]> {
    console.log(this.globalURL+"songs/"+searchBy+"/"+searchTerm+"/"+sortBy+"/"+order)
    return this.http.get<SongInView[]>(this.globalURL+"songs/"+searchBy+"/"+searchTerm+"/"+sortBy+"/"+order)
  }
}
