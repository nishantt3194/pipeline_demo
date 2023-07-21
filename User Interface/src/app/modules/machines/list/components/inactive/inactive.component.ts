import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {HeaderItem, Page} from 'app/modules/app-common/models/app-common.models';
import {MachineHeaders} from 'app/modules/machines/dao/machines.headers';
import {MachineMinimalRef} from 'app/modules/machines/dao/machines.models';
import {MachinesService} from 'app/modules/machines/services/machines.service';
import {Observable, Subject} from 'rxjs';

@Component({
    selector: 'app-machines-inactive',
    templateUrl: './inactive.component.html',
    styleUrls: ['./inactive.component.scss']
})
export class InactiveComponent implements OnInit {
    headers: HeaderItem[];
    builder: (page: number, size: number, filters?: any) => Observable<Page<MachineMinimalRef>>;
    private _unsubscribeAll: any;

    constructor(private _machinesService: MachinesService,
                private _router: Router,
                public modalService: NgbModal) {

        this._unsubscribeAll = new Subject();
        this.headers = MachineHeaders;
        this.builder = _machinesService.fetchMachinesList;
    }


    ngOnInit(): void {
    }


    openMachineView(machine: any): void {
        this._router.navigate(["/machines/view"], {queryParams:{id: machine.id}});

    }

}
