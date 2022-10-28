import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { HomepageComponent } from './homepage/homepage.component'
import { LoginComponent } from './login/login.component'
import { CreateuserComponent } from './createuser/createuser.component'
import { CollectionviewComponent } from './collectionview/collectionview.component'
import { CollectionpageComponent } from './collectionpage/collectionpage.component'
import { CanActivate } from '@angular/router'
import { CollectionredirectService } from './collectionredirect.service'
import {UsercollectionsredirectService } from './usercollectionsredirect.service'
import {SearchComponent} from "./search/search.component";
import { FriendviewComponent } from './friendview/friendview.component'
import {FriendsredirectService } from './friendsredirect.service'
import { HomeredirectService } from './homeredirect.service'
import { SongbrowserComponent } from './songbrowser/songbrowser.component'
import { SongsredirectService } from './songsredirect.service'

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'create', component: CreateuserComponent },

  { path: 'home', canActivate: [HomeredirectService], component: HomepageComponent },
  { path: 'users/:userID/home', component: HomepageComponent},

  { path: 'collections', canActivate: [UsercollectionsredirectService], component: CollectionviewComponent },
  { path: 'users/:userID/collections', component: CollectionviewComponent},

  { path: 'collection', canActivate: [CollectionredirectService], component: CollectionpageComponent },
  { path: 'users/:userID/collections/:collectionID', component: CollectionpageComponent },

  { path: 'friends', canActivate: [FriendsredirectService], component: FriendviewComponent },
  { path: 'users/:userID/friends', component: FriendviewComponent },

  { path: 'songs', canActivate: [SongsredirectService], component: SongbrowserComponent },
  { path: 'users/:userID/songs', component: SongbrowserComponent },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'search', component: SearchComponent },
  { path: '',   redirectTo: '/login', pathMatch: 'full' },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
