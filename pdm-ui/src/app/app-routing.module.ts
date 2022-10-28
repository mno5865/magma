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

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomepageComponent },
  { path: 'create', component: CreateuserComponent },

  { path: 'collections', canActivate: [UsercollectionsredirectService], component: CollectionviewComponent },
  { path: 'users/:userID/collections', component: CollectionviewComponent},

  { path: 'collection', canActivate: [CollectionredirectService], component: CollectionpageComponent },
  { path: 'collection/:collectionID', component: CollectionpageComponent },

  { path: 'friends', canActivate: [FriendsredirectService], component: FriendviewComponent },
  { path: 'users/:userID/friends', component: FriendviewComponent },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'search', component: SearchComponent },
  { path: '',   redirectTo: '/login', pathMatch: 'full' },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
