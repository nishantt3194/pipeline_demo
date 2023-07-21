import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {HeaderItem, Page,} from "app/modules/app-common/models/app-common.models";
import {MachineHeaders} from "app/modules/machines/dao/machines.headers";
import {MachineMinimalRef} from "app/modules/machines/dao/machines.models";
import {MachinesService} from "app/modules/machines/services/machines.service";
import {Observable, Subject} from "rxjs";
import { map } from "rxjs/operators";

@Component({
    selector: "app-machines-active",
    templateUrl: "./active.component.html",
    styleUrls: ["./active.component.scss"],
})
export class ActiveComponent implements OnInit {
    headers: HeaderItem[];
    builder: (page: number, size: number, args?: any) => Observable<Page<MachineMinimalRef>>;
    private _unsubscribeAll: any;

    constructor(
        private _machinesService: MachinesService,
        private _router: Router,
        public modalService: NgbModal
    ) {
        this._unsubscribeAll = new Subject();
        this.headers = MachineHeaders;
        this.builder = _machinesService.fetchMachinesList;
    }

    ngOnInit(): void {
        
    }

    ngOnDestroy(): void {
        this._unsubscribeAll.next();
        this._unsubscribeAll.complete();
    }

    openMachineView(machine: any): void {
        this._router.navigate(["/machines/view"], {queryParams:{id: machine.id}});

    }
}
