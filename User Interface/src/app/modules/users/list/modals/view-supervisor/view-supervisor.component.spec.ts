import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewSupervisorComponent} from './view-supervisor.component';

describe('ViewSupervisorComponent', () => {
    let component: ViewSupervisorComponent;
    let fixture: ComponentFixture<ViewSupervisorComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ViewSupervisorComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ViewSupervisorComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
