/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ToastrService} from "ngx-toastr";
import {tap} from "rxjs/operators";

@Injectable()
export class PostInterceptor implements HttpInterceptor {

    /**
     * @param {ToastrService} _toastr
     */
    constructor(private _toastr: ToastrService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            tap(event => {
                if (event instanceof HttpResponse) {
                    if ([202, 201].indexOf(event.status) !== -1) {
                        let message: string = event.body.message;
                        if (!message || message.length < 1) message = event.body;
                        this._toastr.success(message, 'Success', {
                            positionClass: 'toast-bottom-right',
                            toastClass: 'toast ngx-toastr',
                            closeButton: true
                        });
                    }
                }
            })
        );
    }
}
