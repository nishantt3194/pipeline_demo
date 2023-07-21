import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Settings_Routes} from "app/api/machine-routes.api";
import {Page} from "app/modules/app-common/models/app-common.models";
import {environment} from "environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
import {AreaDetail, EditShiftRef, NewShiftPayload, ShiftDetail,} from "../dao/settings.models";
import {tap} from "rxjs/operators";
import { Shift_Routes } from "../routes/shift.routes";
import { Area_Routes } from "../routes/area.routes";

@Injectable({
    providedIn: "root",
})
export class SettingsService {

    areaCount: BehaviorSubject<number> = new BehaviorSubject<number>(-1);

    headers = new HttpHeaders({
        "Content-Type": "application/json",
    });

    constructor(private http: HttpClient) {

        this.fetchShiftList = this.fetchShiftList.bind(this);
        this.fetchAreaList = this.fetchAreaList.bind(this);


    }

    createNewShift(payload: NewShiftPayload) {
        let url = environment.apiUrl + Shift_Routes.Shift_New;

        return this.http.post<string>(url, payload, {
            headers: this.headers,
            responseType: "text" as "json",
        });
    }

    registerArea(payload: string): Observable<string> {
        let url = environment.apiUrl + Settings_Routes.Register_Area;
        return this.http.post<string>(url, payload, {
            headers: this.headers,
            responseType: "text" as "json",
        });
    }

   

    fetchShiftList(page: number, size: number): Observable<Page<ShiftDetail>> {
        let url = environment.apiUrl + Shift_Routes.Shift_List;

        return this.http.get<Page<ShiftDetail>>(url, {
            params: new HttpParams().set("offset", page).set("size", size),
        });
    }


    fetchAreaList(page: number, size: number): Observable<Page<AreaDetail>> {
        let url = environment.apiUrl + Area_Routes.Area_List;

        return this.http.get<Page<AreaDetail>>(url, {
            params: new HttpParams().set("offset", page).set("size", size),
        }).pipe(tap(pages => {
            this.areaCount.next(pages.content.length);
            return pages;
        }));

    }

}
