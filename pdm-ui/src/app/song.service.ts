import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Song } from './Song';
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
}
