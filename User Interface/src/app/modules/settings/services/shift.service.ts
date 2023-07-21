import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { BehaviorSubject, Observable } from "rxjs";
import {
  EditShiftRef,
  NewShiftPayload,
  ShiftDetail,
  ShiftSearch,
} from "../dao/settings.models";
import { environment } from "../../../../environments/environment";
import { ShiftRoutes, Shift_Routes } from "../routes/shift.routes";
import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class ShiftService {
  shiftInfo: ShiftDetail;

  onShiftChange: BehaviorSubject<ShiftDetail> =new BehaviorSubject<any>({});
  constructor(private _http: HttpClient) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {
    return new Promise<void>((resolve, reject) => {
      let id: string = route.queryParams.id;

      Promise.all([this.info(id)]).then(() => {
        resolve();
      }, reject);
    });
  }

  search(area: string, machine: string,date: string): Observable<ShiftSearch[]> {
    let url = environment.apiUrl + ShiftRoutes.search;
    let params = new HttpParams();
    params = params.set("area" , area).set("machine" , machine).set("date" , date)
    

    return this._http.get<ShiftSearch[]>(url, { params: params });
  }

  info(id: string) {
    return new Promise((resolve, reject) => {
      let url = environment.apiUrl + Shift_Routes.Shift_Info;

      this._http
        .get<any>(url, { params: new HttpParams().set("id", id) })
        .subscribe((info) => {

          this.shiftInfo = info;
          this.onShiftChange.next(info);
          resolve(this.shiftInfo);
        }, reject);
    });
  }



  // fetch(id: string) {
  //   return new Promise((resolve, reject) => {
  //     let url = environment.apiUrl + GroupRoutes.INFO;
  //     this._http
  //       .get<any>(url, {
  //         params: new HttpParams().set("id", id),
  //       })
  //       .subscribe((info) => {
  //         this.groupInfo = info;
  //         this.onGroupChange.next(info);

  //         resolve(this.groupInfo);
  //       }, reject);
  //   });
  // }
  create(payload: NewShiftPayload) {
    const url = environment.apiUrl + Shift_Routes.Shift_New;

    return this._http.post<string>(url, payload, {
      responseType: "text" as "json",
    });
  }

  editShift(payload: EditShiftRef) {
    let url = environment.apiUrl +Shift_Routes.Shift_Edit;
    return this._http.put<string>(url, payload);
  }
}
