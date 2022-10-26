import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';
import { User } from '../User';

@Component({
  selector: 'app-search',
  templateUrl: './searchpage.html',
  styleUrls: ['./login.component.css']
})
export class SearchComponent implements OnInit {

  constructor(private router : Router) { }

  ngOnInit(): void {
  }


  public search(){
    alert('search');
  }

  //search(searchTerm: string, searchby : string, sortby : string): void {
   // console.log(searchTerm, searchby, sortby);
  //}

  // goToCreate(): void {
  //   this.router.navigate(['/', 'create'])
  // }
}
