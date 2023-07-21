import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewOperatorComponent} from './view-operator.component';

describe('ViewOperatorComponent', () => {
    let component: ViewOperatorComponent;
    let fixture: ComponentFixture<ViewOperatorComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ViewOperatorComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ViewOperatorComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
