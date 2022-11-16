import { TestBed } from '@angular/core/testing';

import { ProfileRedirectService } from './profile-redirect.service';

describe('ProfileRedirectService', () => {
  let service: ProfileRedirectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileRedirectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
