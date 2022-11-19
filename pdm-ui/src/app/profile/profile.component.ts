/**
 * author: Mildness Onyekwere - mno5865
 */
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'
import { User } from "../User";
import { Artist } from "../Artist";
import { UtilsService } from "../utils.service";
import { faFire } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})

export class ProfileComponent implements OnInit {
  faFire = faFire
  userID: number = 0
  userInfo: User = {userID: -1, username: "", password: "admin", email: "", firstName: "", lastName: "",
    creationDate: new Date, accessDate: new Date}
  followersCount: number = 0
  followingCount: number = 0
  collectionCount: number = 0
  artists: Artist[] = []

  constructor(private router : Router, route : ActivatedRoute, private utilService : UtilsService) {
    route.params.subscribe((params) => {
      this.userID = params["userID"]   // this keeps track of the username field of the URL
      this.userInfo.firstName = utilService.getFirstName()
      utilService.getCollectionCount(this.userID).subscribe(count => {
        this.collectionCount = count
      })
      utilService.getFollowersCount(this.userID).subscribe(count => {
        this.followersCount = count
      })
      utilService.getFollowingCount(this.userID).subscribe(count => {
        this.followingCount = count
      })
      utilService.getTopTenArtists(this.userID).subscribe(artists => {
        this.artists = artists
      })
    })
  }

  ngOnInit(): void {

  }

  setFilterByPlays(): void {
    this.utilService.getTopTenArtistsByPlays(this.userID).subscribe(artists => {
      this.artists = artists
    })
  }

  setFilterByCollections(): void {
    this.utilService.getTopTenArtistsByCollection(this.userID).subscribe(artists => {
      this.artists = artists
    })
  }

  clearFilter(): void {
    this.utilService.getTopTenArtists(this.userID).subscribe(artists => {
      this.artists = artists
    })
  }
}
