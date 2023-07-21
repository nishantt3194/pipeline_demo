import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {NewShiftPayload, ShiftDetail} from 'app/modules/settings/dao/settings.models';

@Component({
    selector: 'app-edit-shift',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './edit-shift.component.html',
    styleUrls: ['./edit-shift.component.scss']
})
export class EditShiftComponent implements OnInit {

    @Input() data!: ShiftDetail;
    payload: NewShiftPayload = new NewShiftPayload();
    start!: { hour: number, minute: number };
    stop!: { hour: number, minute: number };
    editing: boolean = false;
    areas!: string[]

    constructor(public modal: NgbActiveModal) {
    }

    ngOnInit(): void {
        console.log("Hi" + this.data);
        if (this.data) {
            this.payload.id = parseInt(this.data.identifier);
            this.payload.name = this.data.name;
            this.payload.area = this.data.area;
            this.payload.startTime = this.data.startTime;
            this.payload.stopTime = this.data.stopTime;
        }
    }

    buildTimeStruct(time: string): { hour: number, minute: number } {
        let temp = time.split(' ')
        let isPM = (temp[1] == 'PM');

        let hour = parseInt(temp[0].split(':')[0]);
        let minute = parseInt(temp[0].split(':')[1]);

        hour = isPM ? hour + 12 : hour;

        return {hour: hour, minute: minute};
    }

    toggleEditing() {
        if (this.data.area === 'All') return;
        this.editing = !this.editing;
    }

    save() {
        this.payload.startTime = this.start.hour.toString().padStart(2, '0') + ':' + this.start.minute.toString().padStart(2, '0');
        this.payload.stopTime = this.stop.hour.toString().padStart(2, '0') + ':' + this.stop.minute.toString().padStart(2, '0');
        this.modal.close({action: 'save', payload: this.payload});
    }

}
