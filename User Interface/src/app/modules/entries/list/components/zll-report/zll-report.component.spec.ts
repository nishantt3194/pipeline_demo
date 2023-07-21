import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZllReportComponent } from './zll-report.component';

describe('ZllReportComponent', () => {
  let component: ZllReportComponent;
  let fixture: ComponentFixture<ZllReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZllReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZllReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
