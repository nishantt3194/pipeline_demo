import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {DowntimeRoutes} from "../routes/downtime.routes";
import {DowntimeSearch, DowntimeStatus} from "../dao/settings.models";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DowntimeService {

  constructor(private _http: HttpClient) { }


  create(payload: {reason: string, defaultCategory: string, status: boolean, type: 'COMMON' | 'STATION_SPECIFIC'}) {
    let url = environment.apiUrl + DowntimeRoutes.create;
    return this._http.post(url, payload, { responseType: 'text' as 'json' });
  }

  map(payload: {reason: string, machine: string, station: string}) {
    let url = environment.apiUrl + DowntimeRoutes.map;
    return this._http.post(url, payload, { responseType: 'text' as 'json' });
  }

  search(machine?: string, category?: string): Observable<DowntimeSearch[]> {
    let url = environment.apiUrl + DowntimeRoutes.search;
    let params = {};
    if (machine) {
        params = {
            machine: machine
        }
    }
    if (category) {
        params = {
          category: category
        }
    }

    return this._http.get<DowntimeSearch[]>(url, {params: params});
  }

  changeStatus(payload:DowntimeStatus[]){
      let url = environment.apiUrl + DowntimeRoutes.status;
      return this._http.put(url,payload,{ responseType: 'text' as 'json' });
    }
  
}
