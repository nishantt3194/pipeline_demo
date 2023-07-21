import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {CategoryService} from "../../services/category.service";
import {CategoryInfo} from "../../dao/settings.models";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import { HeaderItem, Page } from 'app/modules/app-common/models/app-common.models';
import { DowntimeService } from '../../services/downtime.service';
import { DowntimeHeaders } from '../../dao/settings.headers';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddReasonComponent } from '../../modals/add-reason/add-reason.component';
import Swal from 'sweetalert2';
import { MapReasonComponent } from '../../modals/map-reason/map-reason.component';

@Component({
    selector: 'app-container',
    templateUrl: './container.component.html',
    styleUrls: ['./container.component.scss'],
    encapsulation: ViewEncapsulation.None,
})
export class ContainerComponent implements OnInit {
    categories: CategoryInfo[] = [];
    parentCategory: string;

    subCategory: string;

    data: any[] = [];

    headers: HeaderItem[] = DowntimeHeaders;

    constructor(private _categoryService: CategoryService,
        private _downtimeService: DowntimeService,
        private _modalService: NgbModal) {
            this.openAddReasonModal = this.openAddReasonModal.bind(this);
            this.mapReason = this.mapReason.bind(this);
            this.setActive =  this.setActive.bind(this);
            this.setInActive =  this.setInActive.bind(this);
    }

    ngOnInit(): void {
        this._categoryService.categories.subscribe((categories) => {
            if (categories.length > 0) {
                this.categories = categories;
                this.parentCategory = categories[0].identifier;
                this.subCategory = categories[0].subcategory[0].identifier;
                this.fetchReasons(this.subCategory);
            }
        });
    }

    getActiveCategoryOptions() {
        if(this.categories.length < 1)
            return [];

        let index = this.categories.findIndex((category) => category.identifier === this.parentCategory);
        if (index === -1) {
            return [];
        }
        return this.categories[index].subcategory;
    }

    refreshSubCategories(id: string) {
        this.subCategory = this.getActiveCategoryOptions()[0].identifier;
        this.fetchReasons(this.subCategory);
    }

    fetchReasons(id: string) {
            if(id && id.length > 0) {
            this._downtimeService.search(null, id).subscribe((data) => {
                this.data = data;
            });
        }
    }

    openAddReasonModal() {
        let modalRef = this._modalService.open(AddReasonComponent, {centered: true});
        modalRef.componentInstance.parentCategory = this.parentCategory;
        modalRef.componentInstance.subCategory = this.subCategory;
        modalRef.result.then((result) => {
            this.fetchReasons(this.subCategory);
        });
    }

    mapReason($event: any, row: any) {
        console.log(row);
        if(row.type ==='COMMON') {
            Swal.fire({
                icon: 'error',
                title: 'Uh-Oh!',
                text: 'Cannot map Common Reasons, this action is valid only for Station Specific Reasons'
            });
            return;
        }
    

        let modalRef = this._modalService.open(MapReasonComponent, {centered: true});
        modalRef.componentInstance.reason = row.identifier;
        modalRef.componentInstance.reasons = this.data;
        modalRef.componentInstance.parentCategory = this.parentCategory;
        modalRef.componentInstance.subCategory = this.subCategory;

        modalRef.result.then((result) => {
            this.fetchReasons(this.subCategory);
        });
    
    }

    setActive(rows: any[]) {
        let inactiveRows = rows
          .filter((obj) => !obj.status)
          .map((obj) => {
            return {
              id: obj.identifier,
              status: !obj.status,
            };
          });
        this._downtimeService.changeStatus(inactiveRows).subscribe({
          complete: () => {
            location.reload();
          },
        });
      }
    
      setInActive(rows: any[]) {
        let activeRows = rows
          .filter((obj) => obj.status)
          .map((obj) => {
            return {
              id: obj.identifier,
              status: !obj.status,
            };
          });
        
        this._downtimeService.changeStatus(activeRows).subscribe(()=>{
          location.reload();
        });
      }
}
