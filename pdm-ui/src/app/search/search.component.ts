import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor() { }

  search(searchTerm: HTMLInputElement, searchby: HTMLSelectElement, sortby: HTMLSelectElement){
    console.log(searchTerm, searchby, sortby);
    alert('search');
  }
  ngOnInit(): void {
  }

}
