import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {HeaderItem, Page} from 'app/modules/app-common/models/app-common.models';
import {OperationLogHeader} from 'app/modules/logs/dao/logs.headers';
import {OperationLog} from 'app/modules/logs/dao/logs.models';
import {LogsService} from 'app/modules/logs/services/logs.service';
import {Observable, Subject} from 'rxjs';

@Component({
    selector: 'app-machine-logs',
    templateUrl: './machine-logs.component.html',
    styleUrls: ['./machine-logs.component.scss']
})
export class MachineLogsComponent implements OnInit {
    headers: HeaderItem[];
    data: any[];
    builder: (page: number, size: number) => Observable<Page<OperationLog>>;
    private _unsubscribeAll: any;

    constructor(private _logsService: LogsService,
                private _router: Router,
                public modalService: NgbModal) {
        this._unsubscribeAll = new Subject();
        this.headers = OperationLogHeader;
        this.data = [
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': false,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
            {
                'id': 'da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac',
                'name': 'VA-44',
                'status': true,
                'area': 'IVC Molding',
                'totalStations': '4 Stations',
            },
        ]
    }

    ngOnInit(): void {
    }

    ngOnDestroy(): void {
        this._unsubscribeAll.next();
        this._unsubscribeAll.complete();
    }

}
