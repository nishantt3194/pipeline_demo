import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {HeaderItem, Page} from "app/modules/app-common/models/app-common.models";
import {ProductHeaders,} from "app/modules/products/dao/products.headers";
import {ProductRef} from "app/modules/products/dao/products.models";
import {ProductsService} from "app/modules/products/services/products.service";
import {Observable, Subject} from "rxjs";
import {EditProductComponent} from "../../modals/edit-product/edit-product.component";
import {NewProductComponent} from "../../modals/new-product/new-product.component";

@Component({
    selector: "app-overview",
    templateUrl: "./overview.component.html",
    styleUrls: ["./overview.component.scss"],
})
export class OverviewComponent implements OnInit {
    headers: HeaderItem[];
    builder: (page: number, size: number) => Observable<Page<ProductRef>>;
    private _unsubscribeAll: any;

    constructor(
        private _productService: ProductsService,
        private _router: Router,
        public modalService: NgbModal
    ) {
        this.openNewProductDialog = this.openNewProductDialog.bind(this);
        this._unsubscribeAll = new Subject();
        this.headers = ProductHeaders;
        this.builder = _productService.fetchProductsList;

    }

    openNewProductDialog() {
        const modalOptions: NgbModalOptions = {
            centered: true,
        };

        const modalRef = this.modalService.open(NewProductComponent, modalOptions);
    }

    ngOnInit(): void {
    }

    openProductView() {
        const modalOptions: NgbModalOptions = {
            centered: true,
        };

        const modalRef = this.modalService.open(EditProductComponent, modalOptions);
    }
}
