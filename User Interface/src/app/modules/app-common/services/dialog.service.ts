import {Injectable} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ErrorDialogComponent} from "../modals/error-dialog/error-dialog.component";

@Injectable({
    providedIn: 'root'
})
export class DialogService {

    constructor(private _modalService: NgbModal) {
        this.error = this.error.bind(this);
    }

    error(message: string) {
        const modalRef = this._modalService.open(ErrorDialogComponent, {centered: true});
        modalRef.componentInstance.message = message;
    }
}
