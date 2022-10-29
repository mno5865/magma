import { TestBed } from '@angular/core/testing';

import { CollectionredirectService } from './collectionredirect.service';

describe('CollectionredirectService', () => {
  let service: CollectionredirectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CollectionredirectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
