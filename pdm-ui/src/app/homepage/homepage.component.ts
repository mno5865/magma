/// file/component: Home Page
/// description: A home page for users to access different parts of the site
/// author: Adrian Burgos - awb8593, Mildness Onyekwere - mno5865

import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { faPlay } from '@fortawesome/free-solid-svg-icons'
import { UtilsService } from '../utils.service'
import { SongInView } from "../SongInView";
import { Genre } from "../Genre";
import { GenreService } from "../genre.service";
import { SongService } from "../song.service";
import { User } from "../User";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})

export class HomepageComponent implements OnInit {
  faPlay = faPlay
  userID: number = 0
  currentUser: User = {userID: 0, username: "", password: "", firstName: "", lastName: "", creationDate: new Date(),
    accessDate: new Date(), email: ""}
  topFiveGenres: Genre[] = []
  topFiftySongs: SongInView[] = []

  constructor(private router : Router, private utilsService: UtilsService,
              private genreService: GenreService, private songService: SongService,
              route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = this.utilsService.getUserID()
      this.currentUser.firstName = this.utilsService.getFirstName()
      this.genreService.getTopFiveGenres().subscribe(genres => {
        this.topFiveGenres = genres
      })
      this.songService.getTopFiftySongs().subscribe(songs => {
        this.topFiftySongs = songs
      })
    })
  }

  ngOnInit(): void {

  }

  convertRuntime(runtime: number): string {
    if (runtime % 60 < 10) {
      return Math.floor(runtime/60).toString() + ":0" + runtime % 60
    }
    return Math.floor(runtime/60).toString() + ":" + runtime % 60
  }

  listenToSong(songId: number): void {
    this.songService.listenToSong(this.userID, songId).subscribe()
  }
}
