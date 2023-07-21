import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {CategoryRoutes} from "../routes/category.routes";
import {CategoryInfo} from "../dao/settings.models";
import {BehaviorSubject, Observable} from "rxjs";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class CategoryService implements Resolve<void> {
    categories: BehaviorSubject<CategoryInfo[]> = new BehaviorSubject<CategoryInfo[]>([]);

    constructor(private _http: HttpClient) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): void | Observable<void> | Promise<void> {
        return new Promise<void>((resolve, reject) => {
            this.search().subscribe({
                next: (categories) => {
                    this.categories.next(categories);
                },
                complete: () => {
                    resolve();
                },
                error: (error) => {
                    reject(error);
                }
            });
        });
    }

    search(): Observable<CategoryInfo[]> {
        let url = environment.apiUrl + CategoryRoutes.search;
        return this._http.get<CategoryInfo[]>(url);
    }
}
