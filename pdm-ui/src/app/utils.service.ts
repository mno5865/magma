import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {
  private username = "."

  constructor() { }

  public getUsername(): string {
    return this.username
  }

  public setUsername(name: string): void {
    this.username = name
  }
}
