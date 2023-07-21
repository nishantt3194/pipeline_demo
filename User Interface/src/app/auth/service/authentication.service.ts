/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {LoggedInUser} from 'app/auth/models';
import {ToastrService} from 'ngx-toastr';
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {filter, map} from "rxjs/operators";
import {MsalBroadcastService, MsalService} from "@azure/msal-angular";
import {EventType} from "@azure/msal-browser";

@Injectable({providedIn: 'root'})
export class AuthenticationService {

    private currentUserSubject: BehaviorSubject<LoggedInUser> = new BehaviorSubject<LoggedInUser>(null);

    /**
     *
     * @param {HttpClient} _http
     * @param {Router} _router
     * @param {ToastrService} _toastrService
     * @param _msalBroadcastService
     * @param _msalService
     */
    constructor(private _http: HttpClient,
                private _router: Router,
                private _toastrService: ToastrService,
                private _msalBroadcastService: MsalBroadcastService,
                private _msalService: MsalService) {

        _msalBroadcastService.msalSubject$.pipe(
            filter((event) => event.eventType === EventType.LOGIN_SUCCESS)
        ).subscribe((result) => {
            this.refreshUser();
        });

    }

    get currentUser(): Observable<LoggedInUser> {
        if (this.currentUserSubject.value == null) {
            this.currentUserSubject.next(JSON.parse(localStorage.getItem('currentUser')));
        }

        return this.currentUserSubject.asObservable();
    }

    refreshUser() {
        let url = environment.apiUrl + '/sec/info';
        this._http.get<LoggedInUser>(url).subscribe(user => {
            this.currentUserSubject.next(user);
            localStorage.setItem('currentUser', JSON.stringify(user));
        });
    }

    get currentUserValue(): LoggedInUser {
        return this.currentUserSubject.value;
    }


    get is_logged_in(): Observable<boolean> {
        return this.currentUserSubject.pipe(map(user => user != null));
    }

    /**
     * User logout
     *
     */
    logout() {

        this._msalService.logoutRedirect().subscribe(() => {
            localStorage.removeItem('currentUser');
            this.currentUserSubject.next(null);
            this._router.navigate(['/']);
        });
    }

}
