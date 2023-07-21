import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {NewProductPayload} from 'app/modules/products/dao/products.models';

@Component({
    selector: 'app-edit-product',
    templateUrl: './edit-product.component.html',
    styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {

    @Input() payload: NewProductPayload = new NewProductPayload();

    constructor(public modal: NgbActiveModal) {
    }

    ngOnInit(): void {
    }

    update() {
        this.modal.close({action: 'update', identifier: this.payload});
    }
}
