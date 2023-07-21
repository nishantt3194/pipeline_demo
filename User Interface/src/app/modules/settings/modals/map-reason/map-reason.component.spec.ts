import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapReasonComponent } from './map-reason.component';

describe('MapReasonComponent', () => {
  let component: MapReasonComponent;
  let fixture: ComponentFixture<MapReasonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MapReasonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapReasonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
