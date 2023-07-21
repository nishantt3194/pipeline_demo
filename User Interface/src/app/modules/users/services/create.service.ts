import {Injectable} from '@angular/core';
import { CreateUserPayload } from '../dao/users.models';
import { Observable } from 'rxjs';
import { environment } from 'environments/environment';
import { Users_Routes } from 'app/api/users-routes.api';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class CreateService {

    constructor(private _http: HttpClient

    ) {
    }
    create(payload: CreateUserPayload): Observable<string> {
        const url = environment.apiUrl + Users_Routes.Users_Create;

        return this._http.post<string>(url, payload, {
            responseType: 'text' as 'json',
        });
    }
}
