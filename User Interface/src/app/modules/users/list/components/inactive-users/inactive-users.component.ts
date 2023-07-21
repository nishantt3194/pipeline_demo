import {Component, OnInit} from '@angular/core';
import {HeaderItem} from "../../../../app-common/models/app-common.models";
import {UsersHeaders} from "../../../dao/users.headers";
import {ListService} from "../../../services/list.service";

@Component({
    selector: 'app-inactive-users',
    templateUrl: './inactive-users.component.html',
    styleUrls: ['./inactive-users.component.scss']
})
export class InactiveUsersComponent implements OnInit {
    headers: HeaderItem[] = UsersHeaders;
    data: any[]

    constructor(public listService: ListService) {
    }

    ngOnInit(): void {
    }

}
