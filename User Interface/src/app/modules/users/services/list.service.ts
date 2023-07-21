import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { Page } from "../../app-common/models/app-common.models";
import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "../../../../environments/environment";

import { UsersRef } from "../dao/users.models";
import { Users_Routes } from "app/api/users-routes.api";
import { tap } from "rxjs/operators";
import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class ListService {

  supervisor: BehaviorSubject<number> = new BehaviorSubject<number>(-1);

  operator: BehaviorSubject<number> = new BehaviorSubject<number>(-1);
  constructor(private _http: HttpClient) {
    this.fetchUserList = this.fetchUserList.bind(this);
  }


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {
    return new Promise<void>((resolve, reject) => {
      Promise.all([]).then(() => {
        resolve();
      }, reject);
    });
  }
  fetchUserList(
    page: number,
    size: number,
    filters?: any
  ): Observable<Page<UsersRef>> {
    let url = environment.apiUrl + Users_Routes.Users_List;
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
      let roleFilter = Object.keys(filters).includes("role")
      ? filters.role
      : undefined;
    return this._http.get<Page<UsersRef>>(url, {
      params: params,
      responseType: "json",
    }).pipe(
      tap((pages) => {
        let content = pages.content;
        if (roleFilter !== undefined) {
          let filtered = content.filter(
            (m) => m.role === roleFilter
          ).length;
          if (roleFilter === 'SUPERVISOR') this.supervisor.next(filtered);
          else 
          if(roleFilter === 'OPERATOR')this.operator.next(filtered);
          // else this.operator.next(filtered)
        }

        return pages;
      })
    );
  }
}
