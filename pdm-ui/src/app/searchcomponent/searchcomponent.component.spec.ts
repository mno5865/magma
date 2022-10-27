import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchcomponentComponent } from './searchcomponent.component';

describe('SearchcomponentComponent', () => {
  let component: SearchcomponentComponent;
  let fixture: ComponentFixture<SearchcomponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchcomponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchcomponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
