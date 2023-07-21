import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Settings_Routes} from 'app/api/machine-routes.api';
import {Page} from 'app/modules/app-common/models/app-common.models';
import {environment} from 'environments/environment';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {NewTemplatePayload, ProductRef, ProductSearch} from '../dao/products.models';
import {tap} from "rxjs/operators";
import {ProductRoutes} from "../routes/products.routes";

@Injectable({
    providedIn: 'root'
})
export class ProductsService {
    samplePageData: any;
    pageObservable: any;
    sampleData: any;

    productCount: BehaviorSubject<number> = new BehaviorSubject<number>(-1);

    headers = new HttpHeaders(
        {
            'Content-Type': 'application/json',
        },
    );

    constructor(private _http: HttpClient) {
        this.fetchProductsList = this.fetchProductsList.bind(this);

    }


    search(area?: string): Observable<ProductSearch[]> {
        let url = environment.apiUrl + ProductRoutes.search;
        let params = new HttpParams();
        if (area)
            params = params.set("area", area);

        return this._http.get<ProductSearch[]>(url, {params: params});
    }


    fetchProductsList(page: number, size: number): Observable<Page<ProductRef>> {
        let url = environment.apiUrl + ProductRoutes.list;

        return this._http.get<Page<ProductRef>>(url, {
            params: new HttpParams().set("offset", page).set("size", size),
        }).pipe(tap(pages => {
            this.productCount.next(pages.content.length);
        }));

    }

    saveTemplates(payload: NewTemplatePayload[]): Observable<string> {
        let url = environment.apiUrl + Settings_Routes.Save_Templates;
        return this._http.post<string>(url, payload, {headers: this.headers, responseType: 'text' as 'json'});
    }


    fetchAllTemplates(): Observable<any> {
        let url = environment.apiUrl + Settings_Routes.Templates;
        this.sampleData = {
            'AD Molding': [
                {
                    description: 'Total Production',
                    id: 14,
                    templateType: 'TOTAL',
                    sign: 'ADD',
                    type: null,
                    toConvert: false,
                    operation: null,
                    operand: null
                },
                {
                    description: 'Machine Process Wastage',
                    id: 15,
                    templateType: 'TOTAL',
                    sign: 'ADD',
                    type: null,
                    toConvert: false,
                    operation: null,
                    operand: null
                },
                {
                    description: 'Machine Start-up',
                    id: 18,
                    templateType: 'EXTRA',
                    sign: 'ADD',
                    type: null,
                    toConvert: false,
                    operation: null,
                    operand: null
                }
            ],
            'AD Syring Barrel Printing': [
                {
                    description: 'Machine Start-up',
                    id: 34,
                    templateType: 'EXTRA',
                    sign: 'ADD',
                    type: null,
                    toConvert: false,
                    operation: null,
                    operand: null
                },
                {
                    description: 'Machine Start-up',
                    id: 35,
                    templateType: 'EXTRA',
                    sign: 'ADD',
                    type: null,
                    toConvert: false,
                    operation: null,
                    operand: null
                },
            ]
        };
        // return this._http.get(url);
        return of(this.sampleData);
    }

}
