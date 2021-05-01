import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonmodelComponent } from './personmodel.component';

describe('PersonmodelComponent', () => {
  let component: PersonmodelComponent;
  let fixture: ComponentFixture<PersonmodelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonmodelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonmodelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
