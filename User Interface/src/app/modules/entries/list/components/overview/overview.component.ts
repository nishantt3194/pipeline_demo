import {Component, OnDestroy, OnInit} from "@angular/core";
import { Router } from "@angular/router";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import {
  HeaderItem,
  Page,
} from "app/modules/app-common/models/app-common.models";
import { ShiftHeaders } from "app/modules/entries/dao/shifts.headers";
import { EntriesService } from "app/modules/entries/services/entries.service";
import { Observable, Subject, Subscription } from "rxjs";

@Component({
  selector: "app-shift-overview",
  templateUrl: "./overview.component.html",
  styleUrls: ["./overview.component.scss"],
})
export class OverviewComponent implements OnInit, OnDestroy {
  // builder: (offset: number, size: number, args?: any) => Observable<Page<any>>;

  headers: HeaderItem[];
  data: any[] = [];
  subscription: Subscription;

  private _unsubscribeAll = new Subject();

  constructor(
    private _entryService: EntriesService,
    private _router: Router,
    public modalService: NgbModal
  ) {
    this.newEntry = this.newEntry.bind(this);
    this.headers = ShiftHeaders;
    this.editEntry =  this.editEntry.bind(this);
  }

  loadData() {
    if(this.subscription) {
        this.subscription.unsubscribe();
        this.subscription = null;
    }

    this.subscription = this._entryService.list(-1, -1, {})
        .subscribe((blocks) => {
          this.data = blocks.pages.content;
        });
  }

  newEntry() {
    this._router.navigate(["/entries/create"]);
  }

  ngOnInit(): void {
    this.loadData();
  }

  openEntryView(entry: any) {
    this._router.navigate(["/entries/view"], { queryParams: { id: entry.id } });
  }

  editEntry(event: any, row: any) {
    this._router.navigate(["/entries/edit"], {queryParams: {id: row.id}});
  }

  ngOnDestroy(): void {
    if(this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
