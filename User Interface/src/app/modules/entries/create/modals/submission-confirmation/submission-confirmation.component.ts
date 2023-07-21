import {Component, Input, OnInit} from '@angular/core';
import {CreateService} from "../../../services/create.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {NewEntryMetadataRef, NewEntryRef, TemplateRef} from "../../../dao/shifts.models";

@Component({
    selector: 'app-submission-confirmation',
    templateUrl: './submission-confirmation.component.html',
    styleUrls: ['./submission-confirmation.component.scss']
})
export class SubmissionConfirmationComponent implements OnInit {
    payload: NewEntryRef;
    metadata: NewEntryMetadataRef;

    @Input() result: { message: string, result: boolean, expected: number,
        speed: number,
        totalDowntime: number,
        totalRuntime: number,
        totalQuantity: number,};


    constructor(private _createService: CreateService, private _modal: NgbActiveModal) {

    }

    ngOnInit(): void {
        this._createService.payload.subscribe(payload => {
            this.payload = payload;
        });
        this._createService.metadata.subscribe(metadata => {
            this.metadata = metadata;
        });
    }

    close() {
        this._modal.close({action: 'close'});
    }

    submit() {
        this._modal.close({action: 'submit'});
    }

    isValid(num: number) {
        return !(num == null || isNaN(num));
    }
}
