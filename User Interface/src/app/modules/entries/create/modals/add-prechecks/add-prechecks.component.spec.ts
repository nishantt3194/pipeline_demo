import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddPrechecksComponent} from './add-prechecks.component';

describe('AddPrechecksComponent', () => {
    let component: AddPrechecksComponent;
    let fixture: ComponentFixture<AddPrechecksComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [AddPrechecksComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AddPrechecksComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
