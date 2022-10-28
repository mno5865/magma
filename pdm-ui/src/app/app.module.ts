import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {SearchComponent} from "./search/search.component";
import { HomepageComponent } from './homepage/homepage.component';
import { HttpClientModule } from '@angular/common/http';
import { CreateuserComponent } from './createuser/createuser.component';
import { CollectionviewComponent } from './collectionview/collectionview.component';
import { CollectionpageComponent } from './collectionpage/collectionpage.component';
import { FriendviewComponent } from './friendview/friendview.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomepageComponent,
    CreateuserComponent,
    CollectionviewComponent,
    CollectionpageComponent,
    SearchComponent,
    FriendviewComponent
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
