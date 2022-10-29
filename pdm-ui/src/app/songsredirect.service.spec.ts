import { TestBed } from '@angular/core/testing';

import { SongsredirectService } from './songsredirect.service';

describe('SongsredirectService', () => {
  let service: SongsredirectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SongsredirectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
