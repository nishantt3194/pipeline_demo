import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import {
  HeaderItem,
  Page,
} from "app/modules/app-common/models/app-common.models";
import { PendingHeaders } from "app/modules/entries/dao/shifts.headers";
import { EntriesService } from "app/modules/entries/services/entries.service";
import { Observable, Subject, Subscription } from "rxjs";

@Component({
  selector: "app-pending",
  templateUrl: "./pending.component.html",
  styleUrls: ["./pending.component.scss"],
})
export class PendingComponent implements OnInit {
  headers: HeaderItem[];
  data: any[] = [];
  // builder: (offset: number, size: number, args?: any) => Observable<Page<any>>;
  private _unsubscribeAll: any;
  subscription: Subscription;


  constructor(
    private _entryService: EntriesService,
    private _router: Router,
    public modalService: NgbModal
  ) {
    this._unsubscribeAll = new Subject();
    this.headers = PendingHeaders;
    // this.builder = this._entryService.list;
    this.newEntry = this.newEntry.bind(this);
  }

  newEntry() {
    this._router.navigate(["/entries/create"]);
  }

  deleteEntry(entry: any) {
    console.log(entry);
  }

  ngOnInit(): void {
    this.loadData();

  }
  loadData() {
    if(this.subscription) {
        this.subscription.unsubscribe();
        this.subscription = null;
    }

    this.subscription = this._entryService.list(-1, -1, {status:"STAGED"})
        .subscribe((blocks) => {
          this.data = blocks.pages.content;
        });
  }

  openPendingEntry(row: any) {
    this._router.navigate(["/entries/create"], {
      queryParams: {
        id: row.id,
        // saveType:"SAVE"
      },
    });
  }
}
