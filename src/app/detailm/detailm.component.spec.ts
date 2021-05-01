import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailmComponent } from './detailm.component';

describe('DetailmComponent', () => {
  let component: DetailmComponent;
  let fixture: ComponentFixture<DetailmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailmComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
