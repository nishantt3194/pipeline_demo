import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddDowntimeComponent} from './add-downtime.component';

describe('AddDowntimeComponent', () => {
    let component: AddDowntimeComponent;
    let fixture: ComponentFixture<AddDowntimeComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [AddDowntimeComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AddDowntimeComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
