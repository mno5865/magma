import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionpageComponent } from './collectionpage.component';

describe('CollectionpageComponent', () => {
  let component: CollectionpageComponent;
  let fixture: ComponentFixture<CollectionpageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CollectionpageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollectionpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
