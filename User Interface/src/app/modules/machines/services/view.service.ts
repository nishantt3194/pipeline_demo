import { DatePipe } from "@angular/common";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { MachineRoutes, ReasonRoute } from "app/api/machine-routes.api";
import { Page } from "app/modules/app-common/models/app-common.models";
import { PrecheckRef, ShiftInfo } from "app/modules/entries/dao/shifts.models";
import { EntryRoutes } from "app/modules/entries/routes/entries.routes";
import { environment } from "environments/environment";
import { BehaviorSubject, Observable, of } from "rxjs";
import { EditMachineRef, EditPrecheckRef, MachineInfo, MinShift, PrecheckInfo, ReasonSearch, StationInfo } from "../dao/machines.models";

@Injectable({
  providedIn: "root",
})
export class ViewService implements Resolve<any> {
  onMachineChange: BehaviorSubject<MachineInfo> = new BehaviorSubject<any>({});
  onMachineChangeFetchPrecheck:BehaviorSubject<EditPrecheckRef[]> = new BehaviorSubject<any>({});
  fetchCommonReason:BehaviorSubject<ReasonSearch[]> = new BehaviorSubject<any>({});
  
  samplePageData: {
    name: string;
    date: string;
    avail: string;
    perf: string;
    quality: string;
    oee: string;
    machineName: string;
    production: string;
  }[];
  pageObservable: any;
  machineInfo: MachineInfo;
  preCheckInfo:EditPrecheckRef[];
  reasons:ReasonSearch[];

  constructor(private _http: HttpClient, public datePipe: DatePipe) {
    this.fetchShiftMachineList = this.fetchShiftMachineList.bind(this);
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {
    return new Promise<void>((resolve, reject) => {
      let id: string = route.queryParams.id;

      Promise.all([this.info(id),this.searchPrechecks(id),this.fetchReason()]).then(() => {
        resolve();
      }, reject);
    });
  }

  info(id: string) {
    return new Promise((resolve, reject) => {
      let url = environment.apiUrl + MachineRoutes.Machine_Info;
      this._http
        .get<MachineInfo>(url, { params: new HttpParams().set("id", id) })
        .subscribe((response: any) => {
          let machine: MachineInfo = response;

          this.machineInfo = machine;
          this.onMachineChange.next(machine);
          resolve(machine);
        }, reject);
    });
  }

  fetchShiftMachineList(
    page: number,
    size: number
  ): Observable<Page<ShiftInfo>> {
    let url = environment.apiUrl + EntryRoutes.list;
    return this._http.get<Page<ShiftInfo>>(url, {
      params: new HttpParams().set("offset", page).set("size", size),
    });
  }

  utilizationPercentageForRange(id:string,startDate:string,endDate:string):Observable<number>{
    let url= environment.apiUrl+MachineRoutes.UtlizationPercentage;
    return this._http.get<number>(url,{
      params:new HttpParams()
          .set("id",id)
          .set("startDate",startDate)
          .set("endDate",endDate)
    })
  }

  
  editMachine(payload: EditMachineRef) {
    let url = environment.apiUrl +MachineRoutes.Edit;
    return this._http.post<{ message: string; data: MachineInfo }>(url, payload);
  }

  fetchStation(id:string):Observable<StationInfo[]>{
    let url = environment.apiUrl +MachineRoutes.Station_Search;
    return this._http.get<StationInfo[]>(url,{
      params:new HttpParams().set('machine',id)
    });
  }

  fetchReason(){
    return new Promise((resolve,reject)=>{
      let url= environment.apiUrl+ReasonRoute.reasonSearch;
      this._http.get<ReasonSearch[]>(url).subscribe((response)=>{
        this.fetchCommonReason.next(response);
        this.reasons=response;
        resolve(response);
      },reject);
    });
  }

  addPrecheck(payload:PrecheckInfo):Observable<PrecheckInfo>{
    let url= environment.apiUrl+MachineRoutes.Precheck_Info;
    return this._http.post<PrecheckInfo>(url,payload,{responseType: 'text' as 'json'});
  }

  updatePrecheck(payload:EditPrecheckRef):Observable<PrecheckInfo>{
    let url= environment.apiUrl+MachineRoutes.Precheck_Info_Edit;
    return this._http.put<PrecheckInfo>(url,payload,{responseType: 'text' as 'json'});
  }09

  searchPrechecks(id:string){
    return new Promise((resolve, reject) => {
      let url = environment.apiUrl + MachineRoutes.Precheck_Search;
      this._http
        .get<PrecheckRef>(url, { params: new HttpParams().set("machineId", id) })
        .subscribe((response: any) => {
          let prechecks: EditPrecheckRef[] = response;

          this.preCheckInfo = prechecks;
          this.onMachineChangeFetchPrecheck.next(prechecks);
          resolve(prechecks);
        }, reject);
    });
  }

  deletePrecheck(id:string){
    let url = environment.apiUrl+MachineRoutes.Precheck_Delete;
    return this._http.delete(url,{
      params:new HttpParams().set('id',id)
    });
  }

  //   this.samplePageData = [
  //     {
  //       name: "C Shift",
  //       date: "25 Jan 2023",
  //       avail: "95.8",
  //       perf: "88.9",
  //       quality: "98.28",
  //       oee: "83.5",
  //       machineName: "VA-43",
  //       production: "8030",
  //       // rejection:'140',downtime:'20',breakdowns:'Cleaning',speed:'17.6','operator':'--'
  //     },
  //     {
  //       name: "A Shift",
  //       date: "24 Jan 2023",
  //       avail: "95.8",
  //       perf: "88.9",
  //       quality: "98.28",
  //       oee: "83.5",
  //       machineName: "VA-43",
  //       production: "8030",
  //       // rejection:'140',downtime:'20',breakdowns:'Cleaning',speed:'17.6','operator':'--'
  //     },
  //     {
  //       name: "B Shift",
  //       date: "23 Jan 2023",
  //       avail: "95.8",
  //       perf: "88.9",
  //       quality: "98.28",
  //       oee: "83.5",
  //       machineName: "VA-43",
  //       production: "8030",
  //       // rejection:'140',downtime:'20',breakdowns:'Cleaning',speed:'17.6','operator':'--'
  //     },
  //   ];

  //   this.pageObservable = of({
  //     content: this.samplePageData,
  //     number: 1,
  //     totalPages: 1,
  //     first: true,
  //     last: true,
  //     empty: true,
  //     numberOfElements: 10,
  //     totalElements: 100,
  //     size: 10,
  //     sort: { empty: true, sorted: true, unsorted: false },
  //     pageable: {
  //       sort: { empty: true, sorted: true, unsorted: false },
  //       offset: 10,
  //       pageNumber: 10,
  //       pageSize: 10,
  //       paged: true,
  //       unpaged: true,
  //     },
  //   });
  //   return this.pageObservable;
  // }
}
