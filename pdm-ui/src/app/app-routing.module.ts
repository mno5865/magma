import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { CreateuserComponent } from './createuser/createuser.component'

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomepageComponent },
  { path: 'create', component: CreateuserComponent },
  { path: '',   redirectTo: '/login', pathMatch: 'full' },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
