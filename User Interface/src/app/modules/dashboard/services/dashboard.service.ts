import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "environments/environment";
import { Dashboard_Routes } from "../routes/dashboard.routes";
import { Page } from "app/modules/app-common/models/app-common.models";
import { DashboardInfo } from "../dao/dashboard.models";
import { NewEntryRef } from "app/modules/entries/dao/shifts.models";
import { Observable } from "rxjs";
import { AuthenticationService } from "app/auth/service";
import { MachineMinimalRef } from "app/modules/machines/dao/machines.models";

@Injectable({
  providedIn: "root",
})
export class DashboardService {
  fetchDashboardList: any[];
  fetchShiftDetailsList(): any[] {
    throw new Error("Method not implemented.");
  }

  constructor(
    private _http: HttpClient,
    private _authservice: AuthenticationService
  ) {
    this.lastFiveEntries = this.lastFiveEntries.bind(this);
    this.stagedEntries = this.stagedEntries.bind(this);
  }

  resolve(): Observable<void> | Promise<void> | void {
    return new Promise<void>((resolve, reject) => {
      Promise.all([]).then(() => {
        resolve();
      }, reject);
    });
  }
  fetchEntryList(): any[] {
    // // let url = environment.apiUrl + ClientRoutes.list;
    // return new Promise((resolve, reject) => {
    //   this._http.get<Page<ClientMinimalRef>>(url)
    //       .pipe(map(resp => resp.content))
    //       .subscribe(resp => {
    //         this.clientsStore.next(resp);
    //         resolve(resp);
    //       }, reject);
    // });
    return [];
  }
  fetchTopPerformingMachine() {
    
  }
  // lastFiveEntries() {
  //   let url = environment.apiUrl + Dashboard_Routes.Last_Five_Entries;
  //   return this._http.get(url).pipe((data: any) => {
  //     return data;s
  //   });
  // }

  lastFiveEntries(): Observable<any[]> {
    let url = environment.apiUrl + Dashboard_Routes.Last_Five_Entries;
    let id = this._authservice.currentUserValue.id;

    return this._http.get<any[]>(url);
  }

  stagedEntries(): Observable<any[]> {
    let url = environment.apiUrl + Dashboard_Routes.Last_Staged_Entry;
    let id = this._authservice.currentUserValue.id;

    return this._http.get<any[]>(url);
  }
  topMachine(){

    let url = environment.apiUrl + Dashboard_Routes.Top_Performing_Machine;
    return this._http.get<DashboardInfo>(url);

  }


  // info(id: string) {
  //   return new Promise<EntryInfo>((resolve, reject) => {
  //     let url = environment.apiUrl + EntryRoutes.info;
  //     this._http.get<EntryInfo>(url, { params: new HttpParams().set("id", id) })
  //       .subscribe((response) => {
  //         let entryInfo: EntryInfo = response;
  //         this.entryInfo = entryInfo;
  //         this.onEntryChange.next(entryInfo);
  //         resolve(entryInfo);
  //       }, reject);
  //   });
  // }

  
}
