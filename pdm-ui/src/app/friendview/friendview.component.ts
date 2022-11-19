/// file/component: Friend View
/// description: Allows a user to follow new users and shows the user a list of people they follow
/// author: Gregory Ojiem - gro3228, Mildness Onyekwere - mno5865

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { faPlay } from '@fortawesome/free-solid-svg-icons'
import { User } from '../User';
import { UtilsService } from '../utils.service';
import { SongService } from "../song.service";
import { SongInView } from "../SongInView";

@Component({
  selector: 'app-friendview',
  templateUrl: './friendview.component.html',
  styleUrls: ['./friendview.component.css']
})
export class FriendviewComponent implements OnInit {
  faPlay = faPlay
  userID: number = 0
  friendList: User[] = []
  friendInfo: User = {userID: -1, username: "", password: "admin", email: "", firstName: "", lastName: "",
    creationDate: new Date, accessDate: new Date}
  topFiftySongs: SongInView[] = []

  constructor(private utilsService : UtilsService, private songService: SongService,
              private router : Router, route: ActivatedRoute) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
    })
    this.songService.getTopFiftySongsByFollowing(this.userID).subscribe(songs => {
      songs.forEach(song => {
        this.songService.getSongInView(song.songId).subscribe(song => {
          this.topFiftySongs.push(song)
        })
      })
    })
  }

  ngOnInit(): void {
    this.utilsService.getFriends(this.userID).subscribe(friends => {
      this.friendList = friends
    })
  }

  findFriendByEmail(email: string) {
    this.utilsService.getUserByEmail(email).subscribe(returnUser => {
      this.friendInfo = returnUser
      const followInfo = document.getElementsByClassName('follow')[0] as HTMLSelectElement;
      if (followInfo != null) {
        followInfo.style.display = 'block';
      }
    })
  }

  unfollowUser(userID: number, friendID: number) {
    this.utilsService.unfollowFriend(userID, friendID).subscribe(returnVal => {
      this.ngOnInit()
    })
  }

  followUser(userID: number, friendID: number) {
    this.utilsService.followFriend(userID, friendID).subscribe(returnVal => {
      this.ngOnInit()
      const followInfo = document.getElementsByClassName('follow')[0] as HTMLSelectElement;
      if (followInfo != null) {
        followInfo.style.display = 'none';
      }
    })
  }

  listenToSong(songId: number): void {
    this.songService.listenToSong(this.userID, songId).subscribe()
  }

  convertRuntime(runtime: number): string {
    if (runtime % 60 < 10) {
      return Math.floor(runtime/60).toString() + ":0" + runtime % 60
    }
    return Math.floor(runtime/60).toString() + ":" + runtime % 60
  }
}
