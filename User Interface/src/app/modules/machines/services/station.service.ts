import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {MachineRoutes} from "../routes/machines.routes";
import {StationSearch} from "../dao/machines.models";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StationService {

  constructor(private _http: HttpClient) { }

  search(machine: string): Observable<StationSearch[]> {
    let url = environment.apiUrl + MachineRoutes.stationSearch;

    return this._http.get<StationSearch[]>(url, {params: {machine: machine}});
  }
}
