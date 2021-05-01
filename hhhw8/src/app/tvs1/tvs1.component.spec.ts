import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Tvs1Component } from './tvs1.component';

describe('Tvs1Component', () => {
  let component: Tvs1Component;
  let fixture: ComponentFixture<Tvs1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Tvs1Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Tvs1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
