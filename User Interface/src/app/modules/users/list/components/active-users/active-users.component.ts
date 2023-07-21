import {Component, OnInit} from '@angular/core';
import {ListService} from "../../../services/list.service";
import {HeaderItem} from "../../../../app-common/models/app-common.models";

@Component({
    selector: 'app-active-users',
    templateUrl: './active-users.component.html',
    styleUrls: ['./active-users.component.scss']
})
export class ActiveUsersComponent implements OnInit {
    headers: HeaderItem[];
    data: any[]

    constructor(public listService: ListService) {
    }

    ngOnInit(): void {
    }

}
