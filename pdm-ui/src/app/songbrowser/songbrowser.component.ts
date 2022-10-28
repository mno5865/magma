import { Component, OnInit } from '@angular/core';
import { SongService } from '../song.service';
import { SongInView } from '../SongInView';

@Component({
  selector: 'app-songbrowser',
  templateUrl: './songbrowser.component.html',
  styleUrls: ['./songbrowser.component.css']
})
export class SongbrowserComponent implements OnInit {
  songList: SongInView[] = []
  constructor(private songService : SongService) { }

  ngOnInit(): void {
  }

  searchForSongs(searchBy: string, searchTerm: string, sortBy: string, order: string) {
    this.songService.getOrderedSongs(searchBy, searchTerm, parseInt(sortBy) , order).subscribe(returnView => {
      this.songList = returnView
    })
  }
}
