import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';
import { HolidaysInfo, NewHolidaysPayload } from '../dao/settings.models';
import { Page } from 'app/modules/app-common/models/app-common.models';

@Injectable({
  providedIn: 'root'
})
export class HolidaysService {

  headers = new HttpHeaders({
    "Content-Type": "application/json",
  });
  constructor(private _http:HttpClient) { }

  createNewHoliday(payload:NewHolidaysPayload):Observable<NewHolidaysPayload>{
    let url=environment.apiUrl+"/holidays/create";
    return this._http.post<NewHolidaysPayload>(url,payload,{
      headers: this.headers,
      responseType: "text" as "json",
    });
  }

  fetchHolidayslist(page:number,size:number):Observable<Page<HolidaysInfo[]>>{
    let url=environment.apiUrl+"/holidays/list";

    return this._http.get<Page<HolidaysInfo[]>>(url,{
      params: new HttpParams().set("offset",page).set("size",size)
    });
  }
}
