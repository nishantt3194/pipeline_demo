import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {NgForm} from "@angular/forms";
import {CreateService} from "../../../services/create.service";
import {NewEntryMetadataRef, NewEntryRef} from "../../../dao/shifts.models";
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'app-remarks',
    templateUrl: './remarks.component.html',
    styleUrls: ['./remarks.component.scss'],
    encapsulation: ViewEncapsulation.None,

})
export class RemarksComponent implements OnInit {

    payload: NewEntryRef;
    metadata: NewEntryMetadataRef;
    @Input() public save: () => void;
    @Input() public submit: () => void;
    @Input() public stepperPrevious: () => void;

    isNotEdit: boolean = true;
    saveType: string;

    constructor(
        private _createService: CreateService,
        private route: ActivatedRoute) {

    }

    saveEntry(form: NgForm) {
        if (form.valid) {
            this._createService.payload.next(this.payload);
            this.save();
        }
    }

    ngOnInit(): void {
        this._createService.saveType.subscribe(saveType => {
            this.isNotEdit = saveType != 'EDIT';
        });

        this._createService.payload.subscribe(payload => {
            this.payload = payload;
        });
    }

    next() {
        this.submit();
    }

    prev() {
        this.stepperPrevious();
    }
}
