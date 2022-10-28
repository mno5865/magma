import { TestBed } from '@angular/core/testing';

import { SearchredirectService } from './searchredirect.service';

describe('SearchredirectService', () => {
  let service: SearchredirectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SearchredirectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
