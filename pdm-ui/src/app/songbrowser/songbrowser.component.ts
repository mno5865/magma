import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-songbrowser',
  templateUrl: './songbrowser.component.html',
  styleUrls: ['./songbrowser.component.css']
})
export class SongbrowserComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  searchForSongs(searchTerm: string, searchBy: string, sortBy: string, order: string) {
    console.log(searchTerm, searchBy, sortBy, order)
  }
}
