import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {NewShiftPayload} from 'app/modules/settings/dao/settings.models';

@Component({
    selector: 'app-new-shift',
    templateUrl: './new-shift.component.html',
    styleUrls: ['./new-shift.component.scss']
})
export class NewShiftComponent implements OnInit {

    payload: NewShiftPayload = new NewShiftPayload();
    start!: { hour: '', minute: '' };
    stop!: { hour: '', minute: '' };
    areas: string[] = ['IVC', 'HYPO'];

    constructor(public modal: NgbActiveModal) {
    }

    ngOnInit(): void {
    }

    close() {
        this.payload.startTime = this.start.hour.toString().padStart(2, '0') + ':' + this.start.minute.toString().padStart(2, '0');
        this.payload.stopTime = this.stop.hour.toString().padStart(2, '0') + ':' + this.stop.minute.toString().padStart(2, '0');
        this.modal.close({action: 'create', identifier: this.payload});
    }

}
