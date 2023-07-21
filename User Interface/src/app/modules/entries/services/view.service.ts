import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { EntryRoutes } from '../routes/entries.routes';
import { BehaviorSubject, Observable } from 'rxjs';
import { ActivatedRoute, ActivatedRouteSnapshot, Params, Resolve, RouterStateSnapshot } from '@angular/router';
import { EntryInfo } from '../dao/shifts.models';
import { provideInstance } from 'ng-block-ui/block-ui.module';


@Injectable({
  providedIn: "root",
})

export class ViewService implements Resolve<void> {
  onEntryChange: BehaviorSubject<EntryInfo> = new BehaviorSubject<any>({});
  entryInfo: EntryInfo;

  constructor(private _http: HttpClient) { }


  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): void | Promise<void> | Observable<void> {
    return new Promise<void>((resolve, reject) => {
      let entryId: string = route.queryParams.id;
      Promise.all([this.info(entryId)]).then(() => { resolve() }, reject);
    });
  }

  info(id: string) {
    return new Promise<EntryInfo>((resolve, reject) => {
      let url = environment.apiUrl + EntryRoutes.info;
      this._http.get<EntryInfo>(url, { params: new HttpParams().set("id", id) })
        .subscribe((response) => {
          let entryInfo: EntryInfo = response;
          this.entryInfo = entryInfo;
          this.onEntryChange.next(entryInfo);
          resolve(entryInfo);
        }, reject);
    });
  }

  // info(id:string):Observable<EntryInfo>{
  //   let url=environment.apiUrl+EntryRoutes.info;
  //   return this._http.get<EntryInfo>(url,{
  //     params:new HttpParams().set('id',id)
  //   });
  // }
}
