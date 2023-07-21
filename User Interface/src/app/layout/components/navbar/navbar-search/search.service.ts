/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

import {BehaviorSubject} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SearchService {
    // Public
    public search = '';
    public apiData = [];
    public onApiDataChange: BehaviorSubject<any>;
    public onIsBookmarkOpenChange: BehaviorSubject<any>;

    /**
     *
     * @param {HttpClient} _httpClient
     */
    constructor(private _httpClient: HttpClient) {
        this.onApiDataChange = new BehaviorSubject('');
        this.onIsBookmarkOpenChange = new BehaviorSubject(false);
        this.getSearchData();
    }

    /**
     * Get Search Data
     */
    getSearchData(): Promise<any[]> {
        return new Promise((resolve, reject) => {
            this.apiData = [];
            this.onApiDataChange.next(this.apiData);
            resolve(this.apiData);
            // this._httpClient.get('api/search-data').subscribe((response: any) => {
            //
            // }, reject);
        });
    }
}
