import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {CoreSidebarService} from '@core/components/core-sidebar/core-sidebar.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {EntriesService} from 'app/modules/entries/services/entries.service';
import {Subscription} from 'rxjs';
import {CreateService} from "../../../services/create.service";
import {NewEntryMetadataRef, NewEntryRef} from "../../../dao/shifts.models";
import {NgForm} from "@angular/forms";
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'app-downtimes',
    templateUrl: './downtimes.component.html',
    styleUrls: ['./downtimes.component.scss'],
    encapsulation: ViewEncapsulation.None,

})
export class DowntimesComponent implements OnInit {

    payload: NewEntryRef;
    metadata: NewEntryMetadataRef;
    @Input() public save: () => void;
    @Input() public stepperNext: () => void;
    @Input() public stepperPrevious: () => void;
    private _subscription: Subscription;
    isNotEdit: boolean = true;

    constructor(private _createService: CreateService,
                private modalService: NgbModal,
                private _coreSidebarService: CoreSidebarService,
                private route: ActivatedRoute) {

    }

    saveEntry(form: NgForm) {
        if (form.valid) {
            this._createService.payload.next(this.payload);
            this.save();
        }
    }

    next() {
        this.stepperNext();
    }

    prev() {
        this.stepperPrevious();
    }

    ngOnInit(): void {
        this._createService.saveType.subscribe(saveType => {
            this.isNotEdit = saveType != 'EDIT';
        });

        this._createService.payload.subscribe(payload => {
            this.payload = payload;
        });

        this._createService.metadata.subscribe(metadata => {
            this.metadata = metadata;
        });
    }

    ngOnDestroy(): void {
        // this._subscription.unsubscribe();
    }

    addDowntime() {
        this._coreSidebarService.getSidebarRegistry("add-downtime").toggleOpen();
    }

    addPrechecks() {
        this._coreSidebarService.getSidebarRegistry("add-precheck").toggleOpen();
    }

    getReasonTitle(id: string, isPrecheck: boolean, type: 'COMMON' | 'STATION_SPECIFIC') {
        if (isPrecheck) {
            return this.metadata.prechecks.find(d => d.reasonId === id).reason;
        } else if (type === 'COMMON') {
            return this.metadata.commonDowntimes.find(d => d.identifier === id).label;
        } else {
            return this.metadata.stationSpecificDowntimes.find(d => d.identifier === id).label;
        }
    }

    deleteDowntime(i: number) {
        this.payload.downtimes.splice(i, 1);
        this._createService.payload.next(this.payload);
    }
}
