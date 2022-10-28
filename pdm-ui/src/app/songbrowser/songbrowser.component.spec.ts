import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SongbrowserComponent } from './songbrowser.component';

describe('SongbrowserComponent', () => {
  let component: SongbrowserComponent;
  let fixture: ComponentFixture<SongbrowserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SongbrowserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SongbrowserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
