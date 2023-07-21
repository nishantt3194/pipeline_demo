import {
  Component,
  Input,
  OnInit,
  ViewEncapsulation
} from "@angular/core";
import { NewEntryMetadataRef, NewEntryRef } from "../../../dao/shifts.models";
import { CreateService } from "../../../services/create.service";
import { NgForm } from "@angular/forms";
import { TemplateMinimal } from "../../../../products/dao/products.models";
import { combineLatest } from "rxjs";
import { repeaterAnimation } from "../../../../app-common/models/app-common.animations";
import { ActivatedRoute } from "@angular/router";
import { log } from "console";

@Component({
  selector: "app-production",
  templateUrl: "./production.component.html",
  styleUrls: ["./production.component.scss"],
  encapsulation: ViewEncapsulation.None,
  animations: [repeaterAnimation],
})
export class ProductionComponent implements OnInit {
  metadata: NewEntryMetadataRef;

  payload: NewEntryRef;
  @Input() public save: () => void;
  @Input() public stepperNext: () => void;
  @Input() public stepperPrevious: () => void;

  // Variables for Molding areas
  productions: number[] = [];
  rejections: number[] = [];
  goodProduction: number = 0;
  totalRejection: number = 0;
  rejectionPercentage: number = 0;

  prodTitle: string;
  rejTitle: string;
  rejTitleShow: boolean = false;

  productionForm: {
    template: TemplateMinimal;
    id: string;
    value?: number;
    calculatedValue?: number;
    type: "PRODUCTION" | "REJECTION" | "TOTAL" | "EXTRA";
  }[] = [];

  rejectionForm: {
    template: TemplateMinimal;
    id: string;
    value?: number;
    calculatedValue?: number;
    type: "PRODUCTION" | "REJECTION" | "TOTAL" | "EXTRA";
  }[] = [];

  isNotEdit: boolean = true;
  saveType: string;
  constructor(
    private _createService: CreateService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._createService.saveType.subscribe((saveType) => {
      this.isNotEdit = saveType != "EDIT";
    });

    combineLatest([
      this._createService.metadata,
      this._createService.payload,
    ]).subscribe(([metadata, payload]) => {
      this.metadata = metadata;
      this.payload = payload;
      this.buildForms();

      if (metadata && payload) {
        this.populateForm(payload);
      }
    });
  }

  populateForm(payload: NewEntryRef) {
    payload.quantities.forEach((quantity) => {
      const prod = this.productionForm
        .map((form) => form.id)
        .indexOf(quantity.template);
      if (prod > -1) {
        this.productionForm[prod].value = quantity.value;
        this.calculateFinalProductionValue(quantity.value, prod);
      } else {
        const rej = this.rejectionForm
          .map((form) => form.id)
          .indexOf(quantity.template);
        if (rej > -1) {
          this.rejectionForm[rej].value = quantity.value;
          this.calculateFinalRejectionValue(quantity.value, rej);
        }
      }
    });

    this.showTotalOrGoodProductionRejection();
    
  }
  
  showTotalOrGoodProductionRejection(){
    if(this.productionForm.length && this.rejectionForm.length){
      if (this.productionForm[0].type === "TOTAL") {
        this.prodTitle = "Good Production";
      } else {
        this.prodTitle = "Total Production";
      }
  
      if (this.rejectionForm.length > 1) {
        this.rejTitle = "Total Rejection";
        this.rejTitleShow = true;
      } else {
        this.rejTitle = "";
        this.rejTitleShow = false;
      }
    }
  }

  buildForms() {
    this.productionForm = [];
    this.rejectionForm = [];

    this.metadata.productions.forEach((template) => {
      this.productionForm.push({
        template: template,
        id: template.id.toString(),
        value: null,
        type: template.templateType,
      });
    });

    this.metadata.rejections.forEach((template) => {
      this.rejectionForm.push({
        template: template,
        id: template.id.toString(),
        value: null,
        type: template.templateType,
      });
    });
  }

  saveEntry(form: NgForm) {
    if (form.valid) {
      this.flushQuantities();
      this._createService.payload.next(this.payload);
      this.save();
    }
  }

  flushQuantities() {
    this.payload.quantities = [];
    this.productionForm.forEach((form) => {
      if (form.value) {
        this.payload.quantities.push({
          template: form.id,
          value: form.value,
          type: form.type,
        });
      }
    });

    this.rejectionForm.forEach((form) => {
      if (form.value) {
        this.payload.quantities.push({
          template: form.id,
          value: form.value,
          type: form.type,
        });
      }
    });
  }

  next(form: NgForm) {
    if (form.valid) {
      this.flushQuantities();
      this._createService.payload.next(this.payload);
      this.stepperNext();
    }
  }

  prev() {
    this.stepperPrevious();
  }

  calculateFinalProductionValue($event: number, i: number) {
    let form = this.productionForm[i];
    this.productionForm[i].value = $event;
    if (form.template.toConvert) {
      let val = $event;
      if (form.template.operation == "DIVIDE") {
        val = $event / form.template.operand;
      } else {
        val = $event * form.template.operand;
      }
      this.productionForm[i].calculatedValue = Math.round(val * 100) / 100;
    } else {
      this.productionForm[i].calculatedValue = $event;
    }
    this.productions[i] = this.productionForm[i].calculatedValue;
    
    this.calculateGoodProduction();
    this.calculateTotalRejection();
    this.rejectionPercentage = parseFloat(
      this.calculateTotalRejectionPercentage().toFixed(2)
    );
  }

  calculateFinalRejectionValue($event: number, i: number) {
    this.rejectionForm[i].value = $event;
    let form = this.rejectionForm[i];
    if (form.template.toConvert) {
      let val = $event;
      if (form.template.operation == "DIVIDE") {
        val = $event / form.template.operand;
      } else {
        val = $event * form.template.operand;
      }
      this.rejectionForm[i].calculatedValue = Math.round(val * 100) / 100;
      if(!this.rejectionForm[i].template.description.toLowerCase().includes("lumps")){
        this.rejections[i] =
        form.template.operation == "DIVIDE"
          ? this.rejectionForm[i].calculatedValue * 1000
          : this.rejectionForm[i].calculatedValue / 1000;
      }
    } else {
      this.rejectionForm[i].calculatedValue = $event;     
      if(!this.rejectionForm[i].template.description.toLowerCase().includes("lumps")){
        this.rejections[i] = this.rejectionForm[i].calculatedValue;
      }
    }

    
    this.calculateGoodProduction();
    this.calculateTotalRejection();
    this.rejectionPercentage = parseFloat(
      this.calculateTotalRejectionPercentage().toFixed(2)
    );
  }

  calculateGoodProduction() {
    let pr = this.productions.reduce((acc, curr) => {
      return acc + curr;
    }, 0);
    let rej = this.rejections.reduce((acc, curr) => {
      return acc + curr;
    }, 0);

    if (this.prodTitle === "Total Production") {
      this.goodProduction = pr + rej;
    } else {
      this.goodProduction = pr - rej;
    }
  }

  calculateTotalRejection() {
    this.totalRejection = this.rejections.reduce((acc, curr) => {
      return acc + curr;
    }, 0);
  }

  calculateTotalRejectionPercentage() {
    let total = this.goodProduction + this.totalRejection;
    return (this.totalRejection / total) * 100;
  }
}
