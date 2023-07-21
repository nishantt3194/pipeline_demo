import { DatePipe } from "@angular/common";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { Page } from "app/modules/app-common/models/app-common.models";
import { environment } from "environments/environment";
import { BehaviorSubject, Observable, of } from "rxjs";
import { MinShift } from "../dao/machines.data";
import {
  BOSData,
  MachineInfo,
  MachineMinimalRef,
  MachineSearch,
  ParetoData,
} from "../dao/machines.models";
import { MachineRoutes, Prechecks } from "../routes/machines.routes";
import { map, tap } from "rxjs/operators";
import { PrecheckSearch } from "app/modules/settings/dao/settings.models";

@Injectable({
  providedIn: "root",
})
export class MachinesService implements Resolve<any> {
  samplePageData: any;
  pageObservable: any;
  shifts!: Observable<MinShift[]>;

  activeMachines: BehaviorSubject<number> = new BehaviorSubject<number>(-1);

  inactiveMachines: BehaviorSubject<number> = new BehaviorSubject<number>(-1);

  constructor(private _http: HttpClient, public datePipe: DatePipe) {
    this.fetchMachinesList = this.fetchMachinesList.bind(this);
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([]).then(() => {
        resolve();
      }, reject);
    });
  }

  search(area?: string, operator?:string): Observable<MachineSearch[]> {
    let url = environment.apiUrl + MachineRoutes.search;
    let params = new HttpParams();
    if (area) params = params.set("area", area );

    return this._http.get<MachineSearch[]>(url, { params: params });
  }
  searchMachine( operator:string): Observable<MachineSearch[]> {
    let url = environment.apiUrl + MachineRoutes.search;
    let params = new HttpParams();
    
    if(operator) params = params.set("operator", operator);

    return this._http.get<MachineSearch[]>(url, { params: params });
  }

  fetchMachinesList(
    page: number,
    size: number,
    filters?: any
  ): Observable<Page<MachineMinimalRef>> {
    let url = environment.apiUrl + MachineRoutes.list;
    let params: HttpParams = new HttpParams()
      .set("offset", page)
      .set("size", size);
    for (const [key, value] of Object.entries(filters)) {
      let val =
        typeof value === "string"
          ? (value as string)
          : typeof value === "boolean"
          ? (value as boolean)
          : (value as number);
      params = params.set(key, val);
    }

    let statusFilter = Object.keys(filters).includes("status")
      ? filters.status
      : undefined;

    return this._http
      .get<Page<MachineMinimalRef>>(url, {
        params: params,
        responseType: "json",
      })
      .pipe(
        tap((pages) => {
          let content = pages.content;
          if (statusFilter !== undefined) {
            let filtered = content.filter(
              (m) => m.status === statusFilter
            ).length;
            if (statusFilter === true) this.activeMachines.next(filtered);
            else this.inactiveMachines.next(filtered);
          }

          return pages;
        })
      );
  }

  // this._machinesService.fetchMachinesList(page, size).pipe(
  //         map((page: Page<MachineMinimalRef>) => {
  //             const filteredItems = page.items.filter((item: MachineMinimalRef) => {
  //                 return item.name.toLowerCase().includes(searchTerm.toLowerCase());
  //             });
  //             return {...page, items: filteredItems};
  //         })
  //         );
  getBOSData(start: Date, end: Date, id: string): Observable<BOSData> {
    let url = environment.apiUrl + MachineRoutes.BOS_Data;

    // this.sampleData = {
    //   'goodQuantities': [24844, 159097, 158287, 145301, 46397],
    //   'lebels': ['01 Jan-01 Jan 22', '02 Jan-08 Jan 22', '09 Jan-15 Jan 22', '16 Jan-22 Jan 22', '23 Jan-29 Jan 22'],
    //   'Isa':[6000, 6000, 6000, 6000, 6000],
    //   'stretched':[8000, 8000, 8000, 8000, 8000],
    //   'weekly':[8281, 7954, 7914, 7265, 6628],
    //   'weekly4':[8281, 8117, 8049, 7853, 7440],
    //   'weekly12':[8281, 8117, 8049, 7853, 7608]
    // };
    return this._http.get<BOSData>(url, {
      params: new HttpParams()
        .set(
          "start",
          this.datePipe.transform(start, "dd-MM-yyyy") || "01-01-2010"
        )
        .set("end", this.datePipe.transform(end, "dd-MM-yyyy") || "01-01-2010")
        .set("id", id),
    });
    // return of(this.sampleData);
  }

  getParetoData(start: Date, end: Date, id: string): Observable<ParetoData> {
    let url = environment.apiUrl + MachineRoutes.Pareto_Data;

    return this._http.get<ParetoData>(url, {
      params: new HttpParams()
        .set(
          "start",
          this.datePipe.transform(start, "dd-MM-yyyy") || "01-01-2010"
        )
        .set("end", this.datePipe.transform(end, "dd-MM-yyyy") || "01-01-2010")
        .set("id", id),
    });
  }

  getPrechecks(machine?: string): Observable<PrecheckSearch[]> {
    let url = environment.apiUrl + Prechecks.Search;
    let params = {};
    if (machine) {
      params = {
        machineId: machine,
      };
    }
    return this._http.get<PrecheckSearch[]>(url, { params: params });
  }
}
