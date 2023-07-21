import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignMachineComponent } from './assign-machine.component';

describe('AssignMachineComponent', () => {
  let component: AssignMachineComponent;
  let fixture: ComponentFixture<AssignMachineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssignMachineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignMachineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
