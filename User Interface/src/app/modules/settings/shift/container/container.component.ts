import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import {HeaderItem, Page} from 'app/modules/app-common/models/app-common.models';
import {Observable, Subject} from 'rxjs';
import {ShiftsHeaders} from '../../dao/settings.headers';
import {NewShiftPayload, ShiftDetail} from '../../dao/settings.models';
import {EditShiftComponent} from '../modals/edit-shift/edit-shift.component';
import {NewShiftComponent} from '../modals/new-shift/new-shift.component';
import {SettingsService} from '../../services/settings.service';
import { ViewShiftComponent } from '../modals/view-shift/view-shift.component';

@Component({
    selector: 'app-container',
    templateUrl: './container.component.html',
    styleUrls: ['./container.component.scss']
})
export class ContainerComponent implements OnInit {
    headers: HeaderItem[];
    builder: (
        page: number,
        size: number
    ) => Observable<Page<ShiftDetail>>;
    private _unsubscribeAll: any;

    constructor(private _settingsService: SettingsService,
                private _router: Router,
                public modalService: NgbModal) {

        this._unsubscribeAll = new Subject();
        this.headers = ShiftsHeaders;
        this.createShift = this.createShift.bind(this);
        this.builder = _settingsService.fetchShiftList;
    }

    ngOnInit(): void {

    }


    openShiftView(row:any) {
        const modalOptions: NgbModalOptions = {
            centered: true,
        }
        let ref = this.modalService.open(ViewShiftComponent, modalOptions);

        ref.componentInstance.ref = row;

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
