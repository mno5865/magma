import { Component } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'pdm-ui';
  public static userID: number = 0;

  constructor(private router : Router, route : ActivatedRoute) {
    route.params.subscribe((params) => {
      let userID = params["userID"]
    })
  }
}
