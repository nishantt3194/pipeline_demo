import {Component, OnInit} from '@angular/core';
import {ProductsService} from "../../services/products.service";

@Component({
    selector: 'app-container',
    templateUrl: './container.component.html',
    styleUrls: ['./container.component.scss']
})
export class ContainerComponent implements OnInit {

    productCount: number;

    constructor(private _productService: ProductsService) {
    }

    ngOnInit(): void {
        this._productService.productCount.subscribe(count => {
            this.productCount = count;
        });
    }

}
