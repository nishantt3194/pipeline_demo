import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPrecheckComponent } from './edit-precheck.component';

describe('EditPrecheckComponent', () => {
  let component: EditPrecheckComponent;
  let fixture: ComponentFixture<EditPrecheckComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditPrecheckComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPrecheckComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
