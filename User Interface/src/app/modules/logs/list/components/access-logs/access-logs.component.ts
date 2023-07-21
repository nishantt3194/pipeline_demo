import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {HeaderItem, Page} from 'app/modules/app-common/models/app-common.models';
import {AccessLogHeader} from 'app/modules/logs/dao/logs.headers';
import {AccessLogRef} from 'app/modules/logs/dao/logs.models';
import {LogsService} from 'app/modules/logs/services/logs.service';
import {Observable, Subject} from 'rxjs';

@Component({
    selector: 'app-access-logs',
    templateUrl: './access-logs.component.html',
    styleUrls: ['./access-logs.component.scss']
})
export class AccessLogsComponent implements OnInit {
    headers: HeaderItem[];
    data: any[];
    builder: (page: number, size: number) => Observable<Page<AccessLogRef>>;
    private _unsubscribeAll: any;

    constructor(private _logsService: LogsService,
                private _router: Router,
                public modalService: NgbModal) {
        this._unsubscribeAll = new Subject();
        this.headers = AccessLogHeader;
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
