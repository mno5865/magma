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

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomepageComponent },
  { path: 'create', component: CreateuserComponent },
  { path: 'users', canActivate: [UsercollectionsredirectService], component: CollectionviewComponent },
  { path: 'users/:userID/collections', component: CollectionviewComponent},
  { path: 'collections', canActivate: [CollectionredirectService], component: CollectionpageComponent },
  { path: 'users/:userID/collections/:collectionID', component: CollectionpageComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'search', component: SearchComponent },
  { path: '',   redirectTo: '/login', pathMatch: 'full' },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
