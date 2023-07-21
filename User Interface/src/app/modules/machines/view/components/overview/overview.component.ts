import { StationInfo } from './../../../dao/machines.models';
import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { NgbModal, NgbModalOptions } from "@ng-bootstrap/ng-bootstrap";
import {
  HeaderItem,
  Page,
} from "app/modules/app-common/models/app-common.models";
import { ReportsHeaders } from "app/modules/entries/dao/shifts.headers";
import { ShiftInfo } from "app/modules/entries/dao/shifts.models";
import { EntriesService } from "app/modules/entries/services/entries.service";
import { MinShift } from "app/modules/machines/dao/machines.data";
import { ShiftHeaders } from "app/modules/machines/dao/machines.headers";
import { EditMachineRef, EditPrecheckRef, MachineInfo } from "app/modules/machines/dao/machines.models";
import { MachinesService } from "app/modules/machines/services/machines.service";
import { ViewService } from "app/modules/machines/services/view.service";
import { ViewSupervisorComponent } from "app/modules/users/list/modals/view-supervisor/view-supervisor.component";
import { FlatpickrOptions } from "ng2-flatpickr";
import { BehaviorSubject, Observable, of, Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
import { EditViewComponent } from "../../modals/edit-view/edit-view.component";
import { EditPrecheckComponent } from "../../modals/edit-precheck/edit-precheck.component";
import { AuthenticationService } from 'app/auth/service';

@Component({
  selector: "app-machine-overview",
  templateUrl: "./overview.component.html",
  styleUrls: ["./overview.component.scss"],
})
export class OverviewComponent implements OnInit {
  ref: MachineInfo;
  private _unsubscribeAll: Subject<any>;
  start: Date;
  end: Date;
  machineId: string;
  utilizationPercentage:string='';
  data: any[]=[];
  prechecks:EditPrecheckRef[];
  stationInfo:StationInfo[];
currentUser: string;
  

  headers: HeaderItem[];
  builder: ( page: number, size: number , id: string ) => Observable<Page<any>>;

  error: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  loading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);

  constructor(
    private _machinesService: MachinesService,
    private _viewService: ViewService,
    private _router: Router,
    public modalService: NgbModal,
    private route: ActivatedRoute,
    private _entryService: EntriesService,
    private _route:ActivatedRoute,
    private _datePipe: DatePipe,
    private _auth:AuthenticationService,

  ) {
    // this.builder = _viewService.fetchShiftMachineList;
    this.headers = ReportsHeaders;
    this._unsubscribeAll = new Subject(); 
    // this.builder = _entryService.list;
    this._route.queryParams.subscribe((machine)=>this.machineId=machine.id);
    
  }

  public rangeOptions: FlatpickrOptions = {
    altInput: true,
    mode: "range",
  };

  setDates($event: Date[]) {
    this.start = $event[0];
    this.end = $event[1];
  }

  ngOnInit(): void {
    this._auth.currentUser.subscribe(res=>this.currentUser=res.role);

    this._viewService.onMachineChange.subscribe({
      next: (machine) => {
        this.ref = machine;
        // this.isEditable = this.ref.status === 'CREATED';
      },
    });
    this._viewService.onMachineChangeFetchPrecheck.subscribe({
      next:(prechecks)=>{
        this.prechecks=prechecks;
      }
    });
    this._entryService.list(-1, -1, )
    .subscribe(blocks => {
        this.data = blocks.pages.content;

        if(!this.data) {
          this.data = [];
        }

      
    });
  }

  generate(){
    let start = this._datePipe.transform(this.start, "dd-MM-yyyy");
    let end = this._datePipe.transform(this.end, "dd-MM-yyyy");
    this._viewService
    .utilizationPercentageForRange(this.machineId,start,end)
    .subscribe(
      (percentage)=>
          this.utilizationPercentage=(percentage.toLocaleString()+"%")
    );
  }
  edit(){
    const modalOptions: NgbModalOptions = {
      centered: true,
      size: "lg",
    };
    let modalRef = this.modalService.open(EditViewComponent, modalOptions);
    let editRef = new EditMachineRef();
    editRef.id = this.ref.id;
    editRef.lsaTarget = this.ref.lsaTarget;
    editRef.prodLimit = this.ref.prodLimit;
    editRef.stretchedTarget = this.ref.stretchedTarget;
    editRef.tolerance = this.ref.tolerance;
    editRef.oeeTarget = this.ref.oeeTarget;
    editRef.baseValue = this.ref.baseValue;
    editRef.speed = this.ref.speed;
    modalRef.componentInstance.ref = editRef;

  }

  getStation(){
    this._viewService.fetchStation(this.machineId).subscribe((response)=>this.stationInfo=response);
  }

  addPrecheck(){
    const modalOptions: NgbModalOptions = {
      centered: true,
      size: "lg",
    };
    let modalRef = this.modalService.open(EditPrecheckComponent, modalOptions);
    let editRef = new EditPrecheckRef();
    editRef.id = this.ref.id;
    
    modalRef.componentInstance.ref = editRef;
  }

  editPrecheck(index:number){
    const modalOptions: NgbModalOptions = {
      centered: true,
      size: "lg",
    };
    let modalRef = this.modalService.open(EditPrecheckComponent, modalOptions);
    
    let editRef = new EditPrecheckRef();
    
    editRef.id=this.prechecks[index].id;
    editRef.time=this.prechecks[index].time;
    
    modalRef.componentInstance.ref = editRef;
    modalRef.componentInstance.flag ="update";
  }

  deleteItem(i:number){
    let item=this.prechecks.splice(i,1);
    this._viewService.deletePrecheck(item[0].id).subscribe((data)=>console.log(data),(error)=>console.log(error));
  }

  
}

