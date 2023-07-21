import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import {
  HeaderItem,
  Page,
} from "app/modules/app-common/models/app-common.models";
import { ReportsHeaders } from "app/modules/entries/dao/shifts.headers";
import { ShiftInfo } from "app/modules/entries/dao/shifts.models";
import { EntriesService } from "app/modules/entries/services/entries.service";
import { MinShift } from "app/modules/machines/dao/machines.data";
import { ShiftHeaders } from "app/modules/machines/dao/machines.headers";
import {
  MachineInfo,
  ShiftHistory,
} from "app/modules/machines/dao/machines.models";
import { MachinesService } from "app/modules/machines/services/machines.service";
import { ViewService } from "app/modules/machines/services/view.service";
import { ShiftDetail } from "app/modules/settings/dao/settings.models";
import { log } from "console";
import { Observable, Subject, Subscription } from "rxjs";

@Component({
  selector: "app-shifts-history",
  templateUrl: "./shifts-history.component.html",
  styleUrls: ["./shifts-history.component.scss"],
})
export class ShiftsHistoryComponent implements OnInit {
  headers: HeaderItem[];
  machine: MachineInfo;
  data: any[] = [];
  private _unsubscribeAll: any;
  start: string;
  end: string;
  id: string;
  subscription: Subscription;

  constructor(
    private _viewService: ViewService,
    private _entryService: EntriesService,
    private route: ActivatedRoute,
    private _datePipe: DatePipe,

    private _router: Router,
    public modalService: NgbModal
  ) {
    this._unsubscribeAll = new Subject();
    this.headers = [...ReportsHeaders];
    route.queryParams.subscribe((data) => (this.id = data.id));
  }
  ngOnDestroy(): void {
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }
  ngOnInit(): void {
    this._entryService.entryRange.subscribe(([start, end]) => {
      this.start = this._datePipe.transform(start, "dd-MM-yyyy");
      this.end = this._datePipe.transform(end, "dd-MM-yyyy");

      this.loadData();
    });
  }

  loadData() {
    if (this.subscription) {
      this.subscription.unsubscribe();
      this.subscription = null;
    }
    if(this.start && this.end){
      this.subscription = this._entryService
      .list(-1, -1, { start: this.start, end: this.end, id: this.id })
      .subscribe((blocks) => {
        this.data = blocks.pages.content;


        if(!this.data) {
          this.data = [];
        }

        let headers = [...ReportsHeaders];

        for (let i = 0; i <= blocks.maxReasons; i++) {
          headers.push({
          field: `breakdown_reason_${i + 1}`,
            header: `Downtime Reason ${i + 1}`,
            type: "text",
            isExtra: true,
          });

          headers.push({
            field: `breakdown_time_${i + 1}`,
            header: `Downtime Time ${i + 1}`,
            type: "text",
            isExtra: true,
          });
        }

        this.headers = headers;

      });
    }
  }

  openEntryView(entry: any) {
    this._router.navigate(["/entries/view"], { queryParams: { id: entry.id } });
  }
}
