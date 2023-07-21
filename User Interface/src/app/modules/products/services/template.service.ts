import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {TemplateMinimal} from "../dao/products.models";
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {TemplateRoutes} from "../routes/template.routes";

@Injectable({
    providedIn: 'root'
})
export class TemplateService {

    constructor(private _http: HttpClient) {
    }

    search(area: string, product: string, type: 'production' | 'rejection'): Observable<TemplateMinimal[]> {
        let url = environment.apiUrl + `${TemplateRoutes.search}/${type}`;
        let params = new HttpParams();
        params = params.set("area", area);
        params = params.set("product", product);

        return this._http.get<TemplateMinimal[]>(url, {params: params});
    }

}
