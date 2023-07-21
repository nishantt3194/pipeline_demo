import { Component, OnInit } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import {
  AreaSearch,
  NewShiftPayload,
} from "app/modules/settings/dao/settings.models";
import { AreaService } from "app/modules/settings/services/area.service";
import { ShiftService } from "app/modules/settings/services/shift.service";
import { FlatpickrOptions } from "ng2-flatpickr";

@Component({
  selector: "app-new-shift",
  templateUrl: "./new-shift.component.html",
  styleUrls: ["./new-shift.component.scss"],
})
export class NewShiftComponent implements OnInit {
  saving: boolean = false;
  areaId: string;

  shiftPayload = new NewShiftPayload();
  start!: { hour: ""; minute: "" };
  stop!: { hour: ""; minute: "" };
  areas: AreaSearch[];

  constructor(
    public modal: NgbActiveModal,
    private _shiftService: ShiftService,
    private _areaService: AreaService
  ) {}

  ngOnInit(): void {
    this._areaService.search().subscribe((areas: AreaSearch[]) => {
      this.areas = areas;
    });
  }
  public basicDateOptions: FlatpickrOptions = {
    altInput: true,
  };

  save() {
    this.saving = true;
    this.shiftPayload.startTime =
    this.start.hour.toString().padStart(2, "0") +
    ":" +
    this.start.minute.toString().padStart(2, "0");
  this.shiftPayload.stopTime =
    this.stop.hour.toString().padStart(2, "0") +
    ":" +
    this.stop.minute.toString().padStart(2, "0");
    this.shiftPayload.area = this.areaId
    this._shiftService.create(this.shiftPayload).subscribe({
      complete: () => {
        this.saving = false;
        this.modal.close({ action: "saved" });
      },
    });
  }

}
