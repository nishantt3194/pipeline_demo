import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {HeaderItem} from 'app/modules/app-common/models/app-common.models';
import {OperationLogHeader} from 'app/modules/logs/dao/logs.headers';
import {LogsService} from 'app/modules/logs/services/logs.service';
import {Subject} from 'rxjs';

@Component({
    selector: 'app-shift-logs',
    templateUrl: './shift-logs.component.html',
    styleUrls: ['./shift-logs.component.scss']
})
export class ShiftLogsComponent implements OnInit {
    headers: HeaderItem[];
    data: any[];
    private _unsubscribeAll: any;

    constructor(private _machinesService: LogsService,
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
