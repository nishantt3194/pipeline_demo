import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from "@angular/core";
import { NgForm } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import {
  EditShiftRef,
  NewShiftPayload,
  ShiftDetail,
} from "app/modules/settings/dao/settings.models";
import { SettingsService } from "app/modules/settings/services/settings.service";
import { ShiftService } from "app/modules/settings/services/shift.service";

@Component({
  selector: "app-edit-shift",
  templateUrl: "./edit-shift.component.html",
  styleUrls: ["./edit-shift.component.scss"],
})
export class EditShiftComponent implements OnInit {
  @Input() ref: EditShiftRef;
  shiftRef: ShiftDetail;
  start!: { hour: number; minute: number };
  stop!: { hour: number; minute: number };
  editing: boolean = false;
  areas!: string[];
  shiftId: string;
  areaId: string;

  submitting: boolean = false;
  constructor(
    public modal: NgbActiveModal,
    private _shiftService: ShiftService,
    private _route: ActivatedRoute,
    public modalService: NgbModal,
    private _settingsService: SettingsService
  ) {
    this._route.queryParams.subscribe((shift) => (this.shiftId = shift.id));
  }

  ngOnInit(): void {}

  buildTimeStruct(time: string): { hour: number; minute: number } {
    let temp = time.split(" ");
    let isPM = temp[1] == "PM";

    let hour = parseInt(temp[0].split(":")[0]);
    let minute = parseInt(temp[0].split(":")[1]);

    hour = isPM ? hour + 12 : hour;

    return { hour: hour, minute: minute };
  }

  save(editForm: NgForm) {
    if (editForm.form.valid) {
      this.submitting = true;

      this.ref.startTime =
        this.start.hour.toString().padStart(2, "0") +
        ":" +
        this.start.minute.toString().padStart(2, "0");
      this.ref.stopTime =
        this.stop.hour.toString().padStart(2, "0") +
        ":" +
        this.stop.minute.toString().padStart(2, "0");
      this.ref.area = this.areaId;

      this._shiftService.editShift(this.ref).subscribe({
        complete: () => {
          this.submitting = false;
          this.modal.close({ action: "saved" });
        },
      });
      window.location.reload();
    }
  }
}
