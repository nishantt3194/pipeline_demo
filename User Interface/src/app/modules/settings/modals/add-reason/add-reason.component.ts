import { Component, Input, OnInit, ViewEncapsulation } from "@angular/core";
import { CategoryInfo } from "../../dao/settings.models";
import { CategoryService } from "../../services/category.service";
import { DowntimeTypes } from "../../dao/settings.data";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { NgForm } from "@angular/forms";
import { DowntimeService } from "../../services/downtime.service";

@Component({
  selector: "app-add-reason",
  templateUrl: "./add-reason.component.html",
  styleUrls: ["./add-reason.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class AddReasonComponent implements OnInit {
  categories: CategoryInfo[];
  @Input() parentCategory: string;
  @Input() subCategory: string;
  reason: string;
  type: 'COMMON' | 'STATION_SPECIFIC';
  types: { label: string; identifier: string }[] = DowntimeTypes;

  constructor(
    private _categoryService: CategoryService,
    private _modal: NgbActiveModal,
    private _downtimeService: DowntimeService
  ) {}

  ngOnInit(): void {
    this._categoryService.categories.subscribe((categories) => {
      this.categories = categories;
    });
  }

  getSubCategories(id: string): CategoryInfo[] {
    let category = this.categories.find(
      (category) => category.identifier === id
    );
    return category ? category.subcategory : [];
  }

  close() {
    this._modal.dismiss();
  }

  save(form: NgForm) {
    if(form.valid) {
      this._downtimeService.create(
        {
          reason: this.reason,
          defaultCategory: this.subCategory,
          status: true,
          type: this.type
        }
      ).subscribe({
        complete: () => {
          this._modal.close();
        }
      })
    }
  }
}
