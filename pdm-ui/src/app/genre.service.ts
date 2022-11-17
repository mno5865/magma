import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Genre } from "./Genre";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  private globalURL = 'http://localhost:8080/api/';

  private genreId: number = 0
  private name: string = ''

  constructor(private http: HttpClient) { }

  getTopFiveGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.globalURL + "genres/top-5")
  }
}
