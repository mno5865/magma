import { TestBed } from '@angular/core/testing';

import { FriendsredirectService } from './friendsredirect.service';

describe('FriendsredirectService', () => {
  let service: FriendsredirectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FriendsredirectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
