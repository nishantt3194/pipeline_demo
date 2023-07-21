import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { EntriesService } from "app/modules/entries/services/entries.service";
import { ViewService } from "app/modules/machines/services/view.service";
import { FlatpickrOptions } from "ng2-flatpickr";
import { EditMachineRef, MachineInfo } from "../../dao/machines.models";
import { EditViewComponent } from "../modals/edit-view/edit-view.component";
import { NgbModal, NgbModalOptions } from "@ng-bootstrap/ng-bootstrap";
import { AuthenticationService } from "app/auth/service";

@Component({
  selector: "app-container",
  templateUrl: "./container.component.html",
  styleUrls: ["./container.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class ContainerComponent implements OnInit {
  currentUser: string;

  role: string;
  start: Date;
  end: Date;
  currentTab: "analysis" | "overview" | "machine-logs" = "overview";
  machineId:string;
  ref:MachineInfo;
  
  constructor(private _entriesService: EntriesService,
    private _viewService:ViewService,
    private _auth:AuthenticationService,
    private route:ActivatedRoute,    public modalService: NgbModal,
    ) {
      route.queryParams.subscribe((data)=>{
        this.machineId=data.id});
        
      this._viewService.onMachineChange.subscribe({
        next: (machine) => {
          this.ref = machine;
        },
      });
    }


  public rangeOptions: FlatpickrOptions = {
    altInput: true,
    mode: "range",
    
  };

  ngOnInit(): void {
    this._auth.currentUser.subscribe(res=>this.currentUser=res.role);

    this.start = new Date();
    this.end = new Date();
  }
  setDates($event: Date[]) {
    this.start = $event[0];
    this.end = $event[1];

    this._entriesService.entryRange.next([this.start, this.end]);
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
}
