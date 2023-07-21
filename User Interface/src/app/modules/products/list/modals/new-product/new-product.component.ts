import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {NewProductPayload} from 'app/modules/products/dao/products.models';

@Component({
    selector: 'app-new-product',
    templateUrl: './new-product.component.html',
    styleUrls: ['./new-product.component.scss']
})
export class NewProductComponent implements OnInit {

    areas!: string[];
    payload: NewProductPayload = new NewProductPayload();

    constructor(public modal: NgbActiveModal) {
    }

    ngOnInit(): void {
    }

    close() {
        this.modal.close({action: 'create', identifier: this.payload});
    }
}
