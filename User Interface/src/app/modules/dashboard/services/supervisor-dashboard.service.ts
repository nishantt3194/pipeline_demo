import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "environments/environment";
import {
  Dashboard_Routes,
  Supervisor_Routes,
} from "../routes/dashboard.routes";
import { Page } from "app/modules/app-common/models/app-common.models";
import { CumulativeDataRef, DashboardInfo, MachineEntryInfo } from "../dao/dashboard.models";
import { EntryInfo, NewEntryRef } from "app/modules/entries/dao/shifts.models";
import { Observable } from "rxjs";
import { AuthenticationService } from "app/auth/service";
import {
  MachineInfo,
  MachineMinimalRef,
} from "app/modules/machines/dao/machines.models";

@Injectable({
  providedIn: "root",
})
export class SupervisorDashboardService {
  constructor(
    private _http: HttpClient,
    private _authservice: AuthenticationService
  ) {
    this.lastThreeEntries = this.lastThreeEntries.bind(this);
  }

  resolve(): Observable<void> | Promise<void> | void {
    return new Promise<void>((resolve, reject) => {
      Promise.all([]).then(() => {
        resolve();
      }, reject);
    });
  }

  lastThreeEntries(): Observable<EntryInfo[]> {
    let url = environment.apiUrl + Supervisor_Routes.Last_Three_Entries;
    return this._http.get<EntryInfo[]>(url);
  }

  topMachines() {
    let url = environment.apiUrl + Supervisor_Routes.Top_Performing_Machines;
    return this._http.get<DashboardInfo>(url);
  }

  totalData(id:string) {
    let url = environment.apiUrl + Supervisor_Routes.Data;
    return this._http.get<CumulativeDataRef>(url,{
      params:new HttpParams().set("machineId",id)
    });
  }

  topPerformingMachine(id:string):Observable<MachineEntryInfo>{
    let url = environment.apiUrl + Supervisor_Routes.Top_Performing_Machines_inArea;
    return this._http.get<MachineEntryInfo>(url,{
      params:new HttpParams().set('area',id)
    })
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
