import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {AreaRoutes} from "../routes/area.routes";
import {AreaSearch} from "../dao/settings.models";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AreaService {
    constructor(private _http: HttpClient) {
    }

    search(): Observable<AreaSearch[]> {
        let url = environment.apiUrl + AreaRoutes.search;

        return this._http.get<AreaSearch[]>(url);
    }
}
