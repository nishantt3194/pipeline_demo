import {Component, OnInit} from '@angular/core';
import {MachinesService} from "../../services/machines.service";

@Component({
    selector: 'app-container',
    templateUrl: './container.component.html',
    styleUrls: ['./container.component.scss']
})
export class ContainerComponent implements OnInit {

    activeCount: number;
    inactiveCount: number;

    constructor(private _machineService: MachinesService) {
    }


    ngOnInit(): void {

        this._machineService.activeMachines.subscribe(count => {
            this.activeCount = count
        });

        this._machineService.inactiveMachines.subscribe(count => {
            this.inactiveCount = count
        });
    }


}
