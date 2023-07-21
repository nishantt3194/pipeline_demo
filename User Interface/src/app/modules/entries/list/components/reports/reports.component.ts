import {Component, Input, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
    HeaderItem,
    Page,
} from "app/modules/app-common/models/app-common.models";
import {ReportsHeaders} from "app/modules/entries/dao/shifts.headers";
import {EntriesService} from "app/modules/entries/services/entries.service";
import {Observable, Subject, Subscription} from "rxjs";
import {DatePipe} from "@angular/common";
import { AuthenticationService } from "app/auth/service";

@Component({
    selector: "app-reports",
    templateUrl: "./reports.component.html",
    styleUrls: ["./reports.component.scss"],
})
export class ReportsComponent implements OnInit {
    // builder: (offset: number, size: number, args?: any) => Observable<Page<any>>;
    start: string;
    end: string;
    headers: HeaderItem[];
    subscription: Subscription;
    data: any[] = [];
    currentUser: string;

    private _unsubscribeAll: any;

    constructor(
        private _entryService: EntriesService,
        private _router: Router,
        public modalService: NgbModal,
        private _datePipe: DatePipe,
        private _auth:AuthenticationService,
    ) {
        // this.builder = this._entryService.list;
        this._unsubscribeAll = new Subject();
        this.headers = [...ReportsHeaders];
        this.newEntry = this.newEntry.bind(this);
        this.editEntry =  this.editEntry.bind(this);
    }

    ngOnInit(): void {
        this._auth.currentUser.subscribe(res=>this.currentUser=res.role);

        this._entryService.entryRange.subscribe(([start, end]) => {
            this.start = this._datePipe.transform(start, "dd-MM-yyyy");
            this.end = this._datePipe.transform(end, "dd-MM-yyyy");
            this.loadData();
           
        });

        if(sessionStorage.getItem('fetchedData')){
            
            let prevData=JSON.parse(localStorage.getItem('fetchedData'));
            let date=JSON.parse(localStorage.getItem('date'));
            this.data=prevData;
            this.start=date.start;
            this.end=date.end;sessionStorage
        }
    }

    loadData() {
        if (this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
        if(this.start && this.end){
            this.subscription = this._entryService
            .list(-1, -1, {start: this.start, end: this.end})
            .subscribe((blocks) => {
                this.data = blocks.pages.content;

                if (!this.data) {
                    this.data = [];
                }
                let headers = [...ReportsHeaders];
                for (let i = 0; i < blocks.maxReasons; i++) {
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

                if(localStorage.getItem('fetchedData')==null){
                    localStorage.setItem('fetchedData',JSON.stringify(this.data));
                    localStorage.setItem('date',JSON.stringify({
                        start:this.start,
                        end:this.end
                    }));
                }
    
            });
        }
    }

    newEntry() {
        this._router.navigate(["/entries/create"]);
    }

    openEntryView(entry: any) { 
        this._router.navigate(["/entries/view"], {queryParams: {id: entry.id}});
    }

    editEntry(event: any, row: any) {
        this._router.navigate(["/entries/edit"], {queryParams: {id: row.id}});
    }
}
