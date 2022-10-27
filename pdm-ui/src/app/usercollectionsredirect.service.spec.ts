import { TestBed } from '@angular/core/testing';

import { UsercollectionsredirectService } from './usercollectionsredirect.service';

describe('UsercollectionsredirectService', () => {
  let service: UsercollectionsredirectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsercollectionsredirectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
