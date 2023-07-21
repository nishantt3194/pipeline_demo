import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import {NewTemplatePayload, Template} from 'app/modules/products/dao/products.models';
import {ProductsService} from 'app/modules/products/services/products.service';
import {BehaviorSubject} from 'rxjs';
import {NewTemplateComponent} from '../../modals/new-template/new-template.component';


@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

    process: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);
    data: any;

    constructor(private router: Router,
                public modalService: NgbModal,
                private productsService: ProductsService) {

        productsService.fetchAllTemplates().subscribe(data => {
            this.data = data;
        }, error => {
        }, () => {
            this.process.next(false)
        });
    }

    get object() {
        return Object;
    }

    ngOnInit(): void {
    }

    openNewTemplateDialog1() {
        const modalOptions: NgbModalOptions = {
            centered: true,

        }
        let ref = this.modalService.open(NewTemplateComponent, modalOptions);
    }

    openNewTemplateDialog(type: string, area: string, templates: Template[]) {
        const modalOptions: NgbModalOptions = {
            centered: true,
        }

        templates = templates.filter(t => t.templateType === type);

        const modalRef = this.modalService.open(NewTemplateComponent, modalOptions);
        modalRef.componentInstance.area = area;
        modalRef.componentInstance.templateType = type;
        modalRef.componentInstance.templates = templates;

        modalRef.result.then(result => {
            if (result === 'Close') return;

            else if (result.action === 'save') {
                let payload: NewTemplatePayload[] = result.payload as NewTemplatePayload[];
                this.productsService.saveTemplates(payload).subscribe(data => {
                    let currentUrl = this.router.url;
                    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
                    this.router.onSameUrlNavigation = 'reload';
                    this.router.navigate([currentUrl]);
                    // this.toastService.show(data, SuccessToastOptions);
                }, error => {
                    // this.toastService.show(error.error, FailureToastOptions);
                });

            }
        });
    }

}
