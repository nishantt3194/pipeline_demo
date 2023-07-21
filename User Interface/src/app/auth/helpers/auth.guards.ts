/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';

import {AuthenticationService} from 'app/auth/service';
import {Observable} from "rxjs";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
    /**
     *
     * @param {Router} _router
     * @param {AuthenticationService} _authenticationService
     */
    constructor(private _router: Router,
                private _authenticationService: AuthenticationService) {
    }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

        return new Promise<boolean>(resolve => {
            this._authenticationService.is_logged_in.subscribe(loggedIn => {
                if (loggedIn) {
                    resolve(true);
                } else {
                    this._router.navigate(['/app/login'], {queryParams: {returnUrl: state.url}});
                    resolve(false);
                }
            });
        });
    }

}
