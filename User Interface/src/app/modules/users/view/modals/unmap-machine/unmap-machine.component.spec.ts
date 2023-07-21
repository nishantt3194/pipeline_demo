import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnmapMachineComponent } from './unmap-machine.component';

describe('UnmapMachineComponent', () => {
  let component: UnmapMachineComponent;
  let fixture: ComponentFixture<UnmapMachineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UnmapMachineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UnmapMachineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
