import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthenticationService} from "../service";
import {environment} from "../../../environments/environment";

@Injectable()
export class ApiInterceptor implements HttpInterceptor {

    constructor(private _authenticationService: AuthenticationService) {
    }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        const isAPI = request.url.startsWith(environment.apiUrl);
        // const token = this._authenticationService.token;
        // if (isAPI && token) {
        //     request = request.clone({
        //         setHeaders: {
        //             Authorization: `Bearer ${token}`
        //         }
        //     });
        // }

        return next.handle(request);
    }
}
