import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {EntryRoutes} from "../routes/entries.routes";
import {BehaviorSubject, Observable} from "rxjs";
import {map, tap} from "rxjs/operators";
import {HeaderItem, Page} from "../../app-common/models/app-common.models";


@Injectable({
  providedIn: "root",
})
export class EntriesService {
  entriesToday: BehaviorSubject<number> = new BehaviorSubject<number>(-1);

  entryRange: BehaviorSubject<Date[]> = new BehaviorSubject<Date[]>([
    new Date(),
    new Date(),
  ]);


  constructor(private _http: HttpClient) {
    this.list = this.list.bind(this);
  }
    areaId: BehaviorSubject<string> = new BehaviorSubject(null);

    
  list( offset: number, size: number, args?: any): Observable<{maxReasons: number, pages: Page<any>}> {
    let url = environment.apiUrl + EntryRoutes.list;
    let params = { offset: offset, size: size , };
    if (args) {
      params = { ...params, ...args };
    }
    
    return this._http.get<Page<any>>(url, { params: params, }).pipe(
      tap((pages) => {
        if (args == null || Object.keys(args).length === 0) {
          this.entriesToday.next(pages.content.length);
        }}
    ),
    map((rawPages) => {
      let pages = rawPages;

      let processed = [];
      let maxIndex = 0;
      rawPages.content.forEach((row) => {
        // reasons: { reason: string, time: number }[]
        let processedRow = row;

        for(let i = 0; i < row.reasons.length; i++) {
          row[`breakdown_reason_${i+1}`] = row.reasons[i].reason;
          row[`breakdown_time_${i+1}`] = row.reasons[i].time;
          if(maxIndex < i) maxIndex = i;
        }
        processed.push(row);
      });


      pages.content = processed;

      return {pages: pages, maxReasons: maxIndex};
    }))
  }
  zll(areaId: string, start: string, end: string) {
    let url = environment.apiUrl + EntryRoutes.zll;
    let params = {area: areaId, start: start, end: end};

    return this._http.get(url, {params: params}).pipe(map((res: any) => {
        let data: {headers: HeaderItem[], data: any[]} = {headers: [], data: []};

        res.headers.forEach((header: any) => {
            data.headers.push({type: 'text', field: header.identifier, header: header.label});
        });

        data.data = res.data;

        return data;
    }));
}
}
