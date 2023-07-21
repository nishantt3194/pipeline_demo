import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { environment } from "../../../../environments/environment";
import { UsersRoutes } from "../routes/users.routes";
import { UsersInfo, UsersRef } from "../dao/users.models";
import { Users_Routes } from "app/api/users-routes.api";
import { map, tap } from "rxjs/operators";
import { MachineMinimalRef } from "app/modules/machines/dao/machines.models";
import { Page } from "app/modules/app-common/models/app-common.models";

@Injectable({
  providedIn: "root",
})
export class ViewService implements Resolve<void> {
  headers = new HttpHeaders({
    "Content-Type": "application/json",
  });
  onUserChange: BehaviorSubject<any> = new BehaviorSubject<any>({});
  userInfo: UsersInfo;

  constructor(private _http: HttpClient) {
    this.getOperatorMachine = this.getOperatorMachine.bind(this);

  }

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<void> | Promise<void> | void {
    return new Promise<void>((resolve, reject) => {
      let id: string = route.queryParams.id;
      Promise.all([this.info(id)]).then(() => {
        resolve();
      }, reject);

      resolve();
    });
  }

  info(id: string) {
    return new Promise((resolve, reject) => {
      let url = environment.apiUrl + UsersRoutes.info;
      this._http
        .get<UsersInfo>(url, { params: new HttpParams().set("id", id) })
        .subscribe((response: any) => {
          let user: UsersInfo = response;
          this.userInfo = user;

          this.onUserChange.next(user);
          resolve(user);
        }, reject);
    });
  }
  unmapOperator(email: string, machine: string) {
    let url = environment.apiUrl + Users_Routes.Operator_Machines;

    return this._http.post<string>(
      url,
      { email: email, machine: machine },
      { headers: this.headers, responseType: "text" as "json" }
    );
  }
  updateUserRole(id: string , status: boolean, role: string, ) {
    let url = environment.apiUrl + Users_Routes.User_Role_Update;
    return this._http
      .post<string>(url, JSON.stringify({id:id, role: role, status: status }), {
        headers: this.headers,
        responseType: "text" as "json",
      })
      .pipe(map((result: string) => result));
  }
  getOperatorMachine(
    offset: number,
    size: number,args?:any
  ): Observable<Page<any>> {
    let url = environment.apiUrl + Users_Routes.Operator_Machines;
    let params = { offset: offset, size: size , };
    if (args) {
      params = { ...params, ...args };
    }
    
   
    return this._http.get<Page<any>>(url, { params: params, }).pipe(
      tap((pages) => {
        if (args == null || Object.keys(args).length === 0) {
        }}
    ))

   
  }
}
