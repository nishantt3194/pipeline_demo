import { Component, Input, OnInit, ViewEncapsulation } from "@angular/core";
import { Subscription } from "rxjs";
import { NewEntryMetadataRef, NewEntryRef } from "../../../dao/shifts.models";
import { CreateService } from "../../../services/create.service";
import { NgForm } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: "app-details",
  templateUrl: "./details.component.html",
  styleUrls: ["./details.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class DetailsComponent implements OnInit {
  metadata: NewEntryMetadataRef;
  payload: NewEntryRef;
  @Input() public isNew: boolean = true;
  @Input() public save: () => void;
  @Input() public stepperNext: () => void;
  @Input() public stepperPrevious: () => void;
  private _subscription: Subscription;
  isNotEdit: boolean = true;

  constructor(
    private _createService: CreateService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._createService.saveType.subscribe((saveType) => {
      this.isNotEdit = saveType != "EDIT";
    });

    this._createService.metadata.subscribe((metadata) => {
      this.metadata = metadata;
    });

    this._createService.payload.subscribe((payload) => {
      this.payload = payload;
    });
  }

  saveEntry(form: NgForm) {
    if (form.valid) {
      this._createService.payload.next(this.payload);
      this.save();
    }
  }

  // next() {
  //     this.stepperNext();
  // }

  next(data: NgForm) {
    if (data.form.valid === true || !this.isNotEdit) {
      this.stepperNext();
    }
  }

  prev() {
    this.stepperPrevious();
  }

  ngOnDestroy(): void {
    // this._subscription.unsubscribe();
  }

  refreshMachineSpecificDetails(machine: string) {
    if (machine != null) {
      this._createService.refreshPrechecks(machine);
      this._createService.refreshStations(machine);
      this._createService.fetchOperators(machine);
      this._createService.refreshStationSpecificDowntimes(machine);
    }
  }

  refreshMachinesAndProducts(area: any,) {
    if (area != null) {
      this._createService.fetchMachines(area);
      this._createService.fetchProducts(area);
    }
  }

  onDateSelection(date: string) {
    var area =this.payload.area;
    var machine = this.payload.machine
    this.payload.machine;
    if (area != null&& machine != null && date != null) {
      this._createService.fetchShifts(area,machine,date);
    }
  }

  refreshTemplates(product: any) {
    if (product != null) {
      this._createService.fetchProductionTemplates(
        this.payload.area,
        product,
        "production"
      );
      this._createService.fetchProductionTemplates(
        this.payload.area,
        product,
        "rejection"
      );
      let productRef = this.metadata.products.find(
        (ref) => ref.identifier == product
      );
      if (productRef) {
        this.metadata.variants = productRef.variants;
        this._createService.metadata.next(this.metadata);
      }
    }
  }
}
