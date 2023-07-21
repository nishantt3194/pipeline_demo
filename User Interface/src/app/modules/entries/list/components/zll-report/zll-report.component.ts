import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {EntriesService} from "../../../services/entries.service";
import {DatePipe} from "@angular/common";
import {AreaSearch} from "../../../../settings/dao/settings.models";
import {FlatpickrOptions} from "ng2-flatpickr";
import {AreaService} from "../../../../settings/services/area.service";
import {HeaderItem} from "../../../../app-common/models/app-common.models";
import { Subject } from 'rxjs';

@Component({
    selector: 'app-zll-report',
    templateUrl: './zll-report.component.html',
    styleUrls: ['./zll-report.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ZllReportComponent implements OnInit {
    start: Date;
    end: Date;
    areaId: string;

    headers: HeaderItem[] = [];
    data: any[] = [];
    areas: AreaSearch[];
    private _unsubscribeAll: any;


    evaluated: boolean = false;

    public rangeOptions: FlatpickrOptions = {
        altInput: true,
        mode: 'range',
    };

    constructor(private _entryService: EntriesService,
                private _areaService: AreaService,
                private _datePipe: DatePipe) {
                this._unsubscribeAll = new Subject();

    }

    ngOnInit(): void {
        this._areaService.search().subscribe((areas: AreaSearch[]) => {
            this.areas = areas;
        });

    }
    ngOnDestroy(): void {
        this._unsubscribeAll.next();
        this._unsubscribeAll.complete();
    }

    setDates($event: Date[]) {
        this.start = $event[0];
        this.end = $event[1];
    }

    generate() {
        let start = this._datePipe.transform(this.start, 'dd-MM-yyyy');
        let end = this._datePipe.transform(this.end, 'dd-MM-yyyy');
        this._entryService.zll(this.areaId, start, end)
            .subscribe((data: any) => {
                this.headers = data.headers;
                this.data = data.data;
                this.evaluated = true;
        });
    }
}
