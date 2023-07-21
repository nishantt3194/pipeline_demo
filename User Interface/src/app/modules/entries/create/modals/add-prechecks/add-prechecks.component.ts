import {AfterViewChecked, AfterViewInit, Component, OnInit} from "@angular/core";
import {CoreSidebarService} from "@core/components/core-sidebar/core-sidebar.service";
import {repeaterAnimation} from "app/modules/app-common/models/app-common.animations";
import {
    DowntimeRef,
    NewEntryMetadataRef, NewEntryRef,
    PrecheckRef,
} from "app/modules/entries/dao/shifts.models";
import {CreateService} from "app/modules/entries/services/create.service";
import {PrecheckSearch} from "app/modules/settings/dao/settings.models";
import {combineLatest} from "rxjs";

@Component({
    selector: "app-add-prechecks",
    templateUrl: "./add-prechecks.component.html",
    styleUrls: ["./add-prechecks.component.scss"],
    animations: [repeaterAnimation],
})
export class AddPrechecksComponent implements OnInit {
    metadata: NewEntryMetadataRef;
    payload: NewEntryRef;
    precheckOptions: { label: string, identifier: string }[] = [];
    prechecks: {
        reason: string;
        time: number;
    }[] = [];

    constructor(
        private _coreSidebarService: CoreSidebarService,
        private _createService: CreateService) {
    }

    ngOnInit(): void {
        combineLatest([this._createService.metadata, this._createService.payload]).subscribe({
            next: data => {
                let metadata = data[0];
                let payload = data[1];
                this.metadata = metadata;
                this.payload = payload;
                this.buildPrechecks(this.metadata, this.payload);
            }
        });
    }

    refresh() {
        this.buildPrechecks(this.metadata, this.payload);
    }

    buildPrechecks(metadata: NewEntryMetadataRef, payload: NewEntryRef) {
        this.prechecks = [];
        let precheckData: Map<string, number> = new Map();
        metadata.prechecks.forEach(precheck => {
            precheckData.set(precheck.reasonId, precheck.time);
        });

        this.precheckOptions = [];
        metadata.prechecks.forEach(precheck => {
            this.precheckOptions.push({
                label: precheck.reason,
                identifier: precheck.reasonId
            })
        });

        payload.downtimes.filter(downtime => precheckData.has(downtime.reason))
            .forEach(downtime => {
                precheckData.set(downtime.reason, downtime.time);
            });

        precheckData.forEach((time, precheck) => {
            this.prechecks.push({
                reason: precheck,
                time: time
            })
        });
    }

    toggleSidebar() {
        this.prechecks = [];
        this._coreSidebarService.getSidebarRegistry("add-precheck").toggleOpen();
    }

    submit() {

        let payload: DowntimeRef[] = [];
        this.prechecks.forEach((precheck) => {
            payload.push({
                reason: precheck.reason,
                time: precheck.time,
                association: null,
                type: 'COMMON',
                manualCategory: '',
                stationName: '',
                precheck: true
            });
        });

        let ref = this._createService.payload.value;
        ref.downtimes = ref.downtimes.filter(downtime => !downtime.precheck);
        ref.downtimes.push(...payload);
        this._createService.payload.next(ref);
        this.toggleSidebar();
    }

    delete(i: number) {
        this.prechecks.splice(i, 1);
    }
}
