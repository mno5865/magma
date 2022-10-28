import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionviewComponent } from './collectionview.component';

describe('CollectionviewComponent', () => {
  let component: CollectionviewComponent;
  let fixture: ComponentFixture<CollectionviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CollectionviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollectionviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
