import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import {HeaderItem} from 'app/modules/app-common/models/app-common.models';
import {ShiftsHeaders} from 'app/modules/settings/dao/settings.headers';
import {NewShiftPayload, ShiftDetail} from 'app/modules/settings/dao/settings.models';
import {SettingsService} from 'app/modules/settings/services/settings.service';
import {Subject} from 'rxjs';
import {NewShiftComponent} from '../../../shift/modals/new-shift/new-shift.component';
import { EditShiftComponent } from '../../modals/edit-shift/edit-shift.component';

@Component({
    selector: 'app-shifts',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './shifts.component.html',
    styleUrls: ['./shifts.component.scss']
})
export class ShiftsComponent implements OnInit {

    headers: HeaderItem[];
    data: any[];
    shiftDetails: any[];
    private _unsubscribeAll: any;

    constructor(private _settingsService: SettingsService,
                private _router: Router,
                public modalService: NgbModal) {

        this._unsubscribeAll = new Subject();
        this.headers = ShiftsHeaders;
        // this.shiftDetails = _settingsService.fetchShiftDetailsList();
        this.createShift = this.createShift.bind(this);
    }

    ngOnInit(): void {
    }


    openShiftView(ids: string[]) {
        const modalOptions: NgbModalOptions = {
            centered: true,
        }
        console.log(ids);
        const modalRef = this.modalService.open(EditShiftComponent, modalOptions);
        let shift: ShiftDetail = new class implements ShiftDetail {
            id: string = 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac';
            name: string = 'VA-44';
            area: string = 'IVC Molding';
            startTime: string = '09:00 PM';
            stopTime: string = '05:00 PM';
        }

        modalRef.componentInstance.data = shift;
        console.log(shift);

        modalRef.result.then(result => {
            if (result === 'cancel') return;

            else if (result.action === 'save') {
                let payload: NewShiftPayload = result.payload as NewShiftPayload;
                this._settingsService.editShift(payload).subscribe(data => {
                    let currentUrl = this._router.url;
                    this._router.routeReuseStrategy.shouldReuseRoute = () => false;
                    this._router.onSameUrlNavigation = 'reload';
                    this._router.navigate([currentUrl]);
                    // this.toastService.show(data, SuccessToastOptions);
                }, error => {
                    // this.toastService.show(error.error, FailureToastOptions);
                });
            }
        });
    }

    createShift() {
        const modalOptions: NgbModalOptions = {
            centered: true,
        }

        const modalRef = this.modalService.open(NewShiftComponent, modalOptions);

        modalRef.result.then(result => {
            if (result === 'Close') return;

            else if (result.action === 'create') {
                let payload: NewShiftPayload = result.identifier as NewShiftPayload;

                this._settingsService.createNewShift(payload).subscribe(
                    data => {
                        let currentUrl = this._router.url;
                        this._router.routeReuseStrategy.shouldReuseRoute = () => false;
                        this._router.onSameUrlNavigation = 'reload';
                        this._router.navigate([currentUrl]);
                        // this.toastService.show(data, SuccessToastOptions);
                    }, error => {
                        // this.toastService.show(error.error, FailureToastOptions);
                    }
                );
            }
        });
    }

}
