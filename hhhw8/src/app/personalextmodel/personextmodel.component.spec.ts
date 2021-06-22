import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonextmodelComponent } from './personextmodel.component';

describe('PersonextmodelComponent', () => {
  let component: PersonextmodelComponent;
  let fixture: ComponentFixture<PersonextmodelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonextmodelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonextmodelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
