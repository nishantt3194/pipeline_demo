import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import {HeaderItem, Page} from 'app/modules/app-common/models/app-common.models';
import {Observable, Subject} from 'rxjs';
import {AreaHeaders} from '../../dao/settings.headers';
import {AreaDetail} from '../../dao/settings.models';
import {NewAreaComponent} from '../../modals/new-area/new-area.component';
import {SettingsService} from '../../services/settings.service';

@Component({
    selector: 'app-container',
    templateUrl: './container.component.html',
    styleUrls: ['./container.component.scss']
})
export class ContainerComponent implements OnInit {
    headers: HeaderItem[];
    builder: (page: number,
              size: number) => Observable<Page<AreaDetail>>;
    areaCount: number;
    private _unsubscribeAll: any;

    constructor(
        private _settingsService: SettingsService,
        private _router: Router,
        public modalService: NgbModal
    ) {
        this.createArea = this.createArea.bind(this);

        this._unsubscribeAll = new Subject();
        this.headers = AreaHeaders;
        this.builder = _settingsService.fetchAreaList;

    }

    ngOnInit(): void {
        this._settingsService.areaCount.subscribe(count => {
            this.areaCount = count;
        })
    }

    createArea() {
        const modalOptions: NgbModalOptions = {
            centered: true,
        };

        const modalRef = this.modalService.open(NewAreaComponent, modalOptions);

        modalRef.result.then((result) => {
            if (result.action === "save") {
                let payload: string = result.identifier as string;
                this._settingsService.registerArea(payload).subscribe(
                    (data) => {
                        let currentUrl = this._router.url;
                        this._router.routeReuseStrategy.shouldReuseRoute = () => false;
                        this._router.onSameUrlNavigation = "reload";
                        this._router.navigate([currentUrl]);
                        // this.toastService.show(data, SuccessToastOptions);
                    },
                    (error) => {
                        // this.toastService.show(error.error, FailureToastOptions);
                    }
                );
            }
        });
    }

}
