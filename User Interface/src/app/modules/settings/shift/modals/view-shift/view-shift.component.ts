import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { EditShiftRef, ShiftDetail } from 'app/modules/settings/dao/settings.models';
import { ShiftService } from 'app/modules/settings/services/shift.service';
import { EditShiftComponent } from '../edit-shift/edit-shift.component';

@Component({
  selector: 'app-view-shift',
  templateUrl: './view-shift.component.html',
  styleUrls: ['./view-shift.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,

})
export class ViewShiftComponent implements OnInit {

  public ref: ShiftDetail;
  start!: { hour: number; minute: number };
  stop!: { hour: number; minute: number };
  editing: boolean = false;
  areas!: string[];
  shiftId: string;
  constructor(
    public modal: NgbActiveModal,
    private _shiftService: ShiftService,
    private _route: ActivatedRoute,
    public modalService: NgbModal,

  ) {
    this._route.queryParams.subscribe((shift) => (this.shiftId = shift.id));
  }

  ngOnInit(): void {
   
    console.log("This is a testing message");
   
  }

  buildTimeStruct(time: string): { hour: number; minute: number } {
    let temp = time.split(" ");
    let isPM = temp[1] == "PM";

    let hour = parseInt(temp[0].split(":")[0]);
    let minute = parseInt(temp[0].split(":")[1]);

    hour = isPM ? hour + 12 : hour;

    return { hour: hour, minute: minute };
  }
  edit(){

    const modalOptions: NgbModalOptions = {
      centered: true,
    };
    let modalRef = this.modalService.open(EditShiftComponent, modalOptions);
    let editRef = new EditShiftRef();
    editRef.id = this.ref.id;
    editRef.name = this.ref.name;
    editRef.area = this.ref.area;
    editRef.startTime = this.ref.startTime;
    console.log("===  fapl  " +   this.ref.startTime + " ==  "  + this.ref.stopTime); 
    editRef.stopTime = this.ref.stopTime;
   
    modalRef.componentInstance.ref = editRef;
  }

 
}
