import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor() { }

  search(searchTerm: string, searchby: string, sortby: string){
    console.log(searchTerm, searchby, sortby);
    alert('search');

  }
  ngOnInit(): void {
  }

}
