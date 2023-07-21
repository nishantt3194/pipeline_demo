import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ShiftInfo} from 'app/modules/entries/dao/shifts.models';

@Component({
    selector: 'app-view',
    templateUrl: './view.component.html',
    styleUrls: ['./view.component.scss']
})
export class ViewComponent implements OnInit {
    data!: ShiftInfo;

    constructor(private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit(): void {
    }

}
