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
export class NotificationsService {
    // Public
    public apiData = [];
    public onApiDataChange: BehaviorSubject<any>;

    /**
     *
     * @param {HttpClient} _httpClient
     */
    constructor(private _httpClient: HttpClient) {
        this.onApiDataChange = new BehaviorSubject('');
        this.getNotificationsData();
    }

    /**
     * Get Notifications Data
     */
    getNotificationsData(): Promise<any[]> {
        return new Promise((resolve) => {
            resolve([]);
        });
    }
}
