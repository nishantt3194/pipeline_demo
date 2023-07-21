import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ShiftsHistoryComponent} from './shifts-history.component';

describe('ShiftsHistoryComponent', () => {
    let component: ShiftsHistoryComponent;
    let fixture: ComponentFixture<ShiftsHistoryComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ShiftsHistoryComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ShiftsHistoryComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
