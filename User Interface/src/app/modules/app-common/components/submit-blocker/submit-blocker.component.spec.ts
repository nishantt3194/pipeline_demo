import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SubmitBlockerComponent} from './submit-blocker.component';

describe('SubmitBlockerComponent', () => {
    let component: SubmitBlockerComponent;
    let fixture: ComponentFixture<SubmitBlockerComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [SubmitBlockerComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(SubmitBlockerComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
