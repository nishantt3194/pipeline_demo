import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-new-area',
    templateUrl: './new-area.component.html',
    styleUrls: ['./new-area.component.scss']
})
export class NewAreaComponent implements OnInit {

    area: string = '';

    constructor(public modal: NgbActiveModal) {
    }

    ngOnInit(): void {
    }

}
