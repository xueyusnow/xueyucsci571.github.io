import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailtComponent } from './detailt.component';

describe('DetailtComponent', () => {
  let component: DetailtComponent;
  let fixture: ComponentFixture<DetailtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailtComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
