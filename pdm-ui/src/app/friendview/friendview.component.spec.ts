import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendviewComponent } from './friendview.component';

describe('FriendviewComponent', () => {
  let component: FriendviewComponent;
  let fixture: ComponentFixture<FriendviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FriendviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FriendviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
