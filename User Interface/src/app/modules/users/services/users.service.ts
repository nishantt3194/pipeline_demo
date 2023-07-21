import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import {
  AssignMachine,
  UserSearch,
  UsersInfo,
  UsersRef,
} from "../dao/users.models";
import { environment } from "../../../../environments/environment";
import { Users_Routes } from "../../../api/users-routes.api";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Page } from "app/modules/app-common/models/app-common.models";
import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { map } from "rxjs/operators";
import { MachineRoutes } from "app/modules/machines/routes/machines.routes";

@Injectable({
  providedIn: "root",
})
export class UsersService {
  headers = new HttpHeaders({
    "Content-Type": "application/json",
  });
  public ref: UsersRef;
  payload: BehaviorSubject<AssignMachine> = new BehaviorSubject<AssignMachine>(
    new AssignMachine()
  );
  onUserChange: BehaviorSubject<UsersRef> = new BehaviorSubject<any>({});

  usersInfo: UsersRef;
  // public onUserChange: BehaviorSubject<any>;
  constructor(private _http: HttpClient) {
    // this.onUserChange = new BehaviorSubject({});
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {
    return new Promise<void>((resolve, reject) => {
      let id: string = route.queryParams.id;

      Promise.all([this.info(id)]).then(() => {
        resolve();
      }, reject);
    });
  }
  search(machine?: string): Observable<UserSearch[]> {
    let url = environment.apiUrl + Users_Routes.Users_Search;
    let params = new HttpParams();
    if (machine) {
      params = params.set("machine", machine);
    }

    return this._http.get<UserSearch[]>(url, { params: params });
  }

  info(id: string) {
    return new Promise((resolve, reject) => {
      let url = environment.apiUrl + Users_Routes.Users_Info;
      this._http
        .get<UsersRef>(url, { params: new HttpParams().set("id", id) })
        .subscribe((info) => {
          this.usersInfo = info;
          this.onUserChange.next(info);
          resolve(this.usersInfo);
        }, reject);
    });
  }
  // fetchUserData(email: string): Observable<UsersRef> {
  //     let url = environment.apiUrl + Users_Routes.Users_Info;
  //     return this._http.get<UsersRef>(url, {params: new HttpParams().set('email', email)});
  // }

  updateUserRole(role: string, status: string) {
    let url = environment.apiUrl + Users_Routes.User_Role_Update;
    return this._http
      .post<string>(url, JSON.stringify({ role: role, status: status }), {
        headers: this.headers,
        responseType: "text" as "json",
      })
      .pipe(map((result: string) => result));
  }

  getOperatorMachineNames(email: string): Observable<string[]> {
    let url = environment.apiUrl + Users_Routes.Operator_Machines;

    return this._http.get<string[]>(url, {
      params: new HttpParams().set("email", email),
    });
  }

  getAllMachineNames(): Observable<string[]> {
    let url = environment.apiUrl + MachineRoutes.search;
    return this._http.get<string[]>(url);
  }
  unmapOperator(id: string, machine: string) {
    let url = environment.apiUrl + Users_Routes.Assign;

    return this._http.post<string>(
      url,
      { id: id, machine: machine },
      { headers: this.headers, responseType: "text" as "json" }
    );
  }
  assign(payload: AssignMachine) {
    let url = environment.apiUrl +Users_Routes.Assign ;
    return this._http.post(url, payload, {responseType: 'text' as 'json'});
}

}
