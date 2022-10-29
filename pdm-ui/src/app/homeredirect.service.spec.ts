import { TestBed } from '@angular/core/testing';

import { HomeredirectService } from './homeredirect.service';

describe('HomeredirectService', () => {
  let service: HomeredirectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HomeredirectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
