import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { LoggedInUser } from "app/auth/models";
import { AuthenticationService } from "app/auth/service";
import { HeaderItem } from "app/modules/app-common/models/app-common.models";
import {
  NewEntriesHeaders,
  StagedEntriesHeaders,
} from "app/modules/dashboard/dao/dashboard.headers";
import { DashboardInfo } from "app/modules/dashboard/dao/dashboard.models";
import { DashboardService } from "app/modules/dashboard/services/dashboard.service";
import { MachineMinimalRef } from "app/modules/machines/dao/machines.models";
import { Subject, Subscription } from "rxjs";
import { takeUntil } from "rxjs/operators";

@Component({
  selector: "app-operator-dashboard",
  templateUrl: "./operator-dashboard.component.html",
  styleUrls: ["./operator-dashboard.component.scss"],
})
export class OperatorDashboardComponent implements OnInit {
  currentUser: LoggedInUser;
  currentDate: string;
  subscription: Subscription;

  public ref: DashboardInfo;
  headers: HeaderItem[];
  lastfiveentriesheaders: HeaderItem[];
  stagedentriesheaders: HeaderItem[];
  private _unsubscribeAll: any;
  lastfivedata: MachineMinimalRef[]=[];
  stageddata: any[] = [];
  // entryData: any[] =[];

  constructor(
    private _dashboardService: DashboardService,
    private _router: Router,
    public modalService: NgbModal,
    private _datePipe:DatePipe,
    private _auth: AuthenticationService
  ) {
    this._unsubscribeAll = new Subject();
    this.lastfiveentriesheaders = NewEntriesHeaders;
    this.stagedentriesheaders = StagedEntriesHeaders;
  }

  ngOnInit(): void {
    this._auth.currentUser.subscribe((user)=>this.currentUser=user);
    this.currentDate=this._datePipe.transform(new Date(),"dd-MMMM-yyyy");
    this._dashboardService.topMachine().subscribe({
      next: (info) => {
        this.ref = info;
      },
    });
    this._dashboardService.lastFiveEntries().subscribe({
      next: (value) => {
        this.lastfivedata = value;
      },
    });

    this._dashboardService.stagedEntries().subscribe({
      next: (data) => {
        this.stageddata = data;
      },
    });
  }

  addNewEntry(){
    this._router.navigate(['/entries/create'])
  }

  RenderChart() {}
}
