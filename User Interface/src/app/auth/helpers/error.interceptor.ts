/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

import {AuthenticationService} from 'app/auth/service';
import {ToastrService} from "ngx-toastr";
import { ThirdPartyDraggable } from '@fullcalendar/interaction';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    /**
     * @param {Router} _router
     * @param {ToastrService} _toastr
     * @param {AuthenticationService} _authenticationService
     */
    constructor(private _router: Router, private _toastr: ToastrService, private _authenticationService: AuthenticationService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            catchError(err => {


                if ([401, 403].indexOf(err.status) !== -1) {
                    // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
                    this._router.navigate(['/pages/miscellaneous/not-authorized']);
                
                    // ? Can also logout and reload if needed
                    this._authenticationService.logout();
                    location.reload();
                }
                
                if([500, 409].indexOf(err.status) !== -1) {
                    this._toastr.error( "Server ran into some problem. Please try again. If the problem persists please contact Administrator", "Error Occurred", {
                        positionClass: 'toast-bottom-right',
                        toastClass: 'toast ngx-toastr',
                        closeButton: true
                    });
                
                    return throwError(err);
                }
                
                if([400, 406].indexOf(err.status) !== -1) {

                    let errorResp = JSON.parse(err.error);
                    this._toastr.error(errorResp.error+". Please try again.", "Error Occurred", {
                        positionClass: 'toast-bottom-right',
                        toastClass: 'toast ngx-toastr',
                        closeButton: true
                    });
                    setTimeout(() => {
                        location.reload();
                    }, 3000);
                    // throwError
                    const error = err.error.message || err.statusText;
                    return throwError(error)
                }
                
                if([404].indexOf(err.status) !== -1) {
                    this._toastr.error( "Could not connect to api. Please contact Administrator", "Error Occurred", {
                        positionClass: 'toast-bottom-right',
                        toastClass: 'toast ngx-toastr',
                        closeButton: true
                    });
                    // throwError
                    const error = err.error.message || err.statusText;
                    return throwError(error)
                }
                
                
                if([200, 201, 202].indexOf(err.status) === -1) {
                    this._toastr.success(err.statusText + "! " + err.error.message, "Error Occurred", {
                        positionClass: 'toast-bottom-right',
                        toastClass: 'toast ngx-toastr',
                        closeButton: true
                    });
                    // throwError
                    const error = err.error.message || err.statusText;
                    return throwError(error);
                }

                return throwError(err);
            })
        );
    }
}
