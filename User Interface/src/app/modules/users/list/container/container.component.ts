import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from "@angular/router";
import { UsersInfo, UsersRef } from '../../dao/users.models';
import { AuthenticationService } from 'app/auth/service';

@Component({
    selector: 'app-container',
    templateUrl: './container.component.html',
    styleUrls: ['./container.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ContainerComponent implements OnInit {

    currentUser: string;
    initial: string = 'active';

    /**
     *
     * @param {Router} _router
     */
    constructor(private _router: Router,
        private _auth:AuthenticationService,
        ) {
        let segment = _router.url.split('/').pop();
        this.initial = segment;
    }

    ngOnInit(): void {
        this._auth.currentUser.subscribe(res=>this.currentUser=res.role);

   
    }

}
