import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {SearchComponent} from "./search/search.component";
import { HttpClientModule } from '@angular/common/http';
import { CreateuserComponent } from './createuser/createuser.component';
import { CollectionviewComponent } from './collectionview/collectionview.component';
import { CollectionpageComponent } from './collectionpage/collectionpage.component';
import { FriendviewComponent } from './friendview/friendview.component';
import { HomepageComponent } from './homepage/homepage.component';
import { SongbrowserComponent } from './songbrowser/songbrowser.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomepageComponent,
    CreateuserComponent,
    CollectionviewComponent,
    CollectionpageComponent,
    FriendviewComponent,
    HomepageComponent,
    SongbrowserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
