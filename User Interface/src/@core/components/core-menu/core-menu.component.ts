/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit, ViewEncapsulation} from '@angular/core';

import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

import {CoreMenuService} from '@core/components/core-menu/core-menu.service';
import {AuthenticationService} from "../../../app/auth/service";
import {LoggedInUser} from "../../../app/auth/models";

@Component({
    selector: '[core-menu]',
    templateUrl: './core-menu.component.html',
    styleUrls: ['./core-menu.component.scss'],
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CoreMenuComponent implements OnInit {
    currentUser: LoggedInUser;

    @Input()
    layout = 'vertical';

    @Input()
    menu: any;

    // Private
    private _unsubscribeAll: Subject<any>;

    /**
     *
     * @param {ChangeDetectorRef} _changeDetectorRef
     * @param {AuthenticationService} _authenticationService
     * @param {CoreMenuService} _coreMenuService
     */
    constructor(private _changeDetectorRef: ChangeDetectorRef,
                private _authenticationService: AuthenticationService,
                private _coreMenuService: CoreMenuService) {
        // Set the private defaults
        this._unsubscribeAll = new Subject();
    }

    // Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void {
        this._authenticationService.currentUser.subscribe({
            next: user => {
                this.currentUser = user;
                this._changeDetectorRef.markForCheck();
            }
        })

        // Set the menu either from the input or from the service
        this.menu = this.menu || this._coreMenuService.getCurrentMenu();

        // Subscribe to the current menu changes
        this._coreMenuService.onMenuChanged.pipe(takeUntil(this._unsubscribeAll)).subscribe(() => {
            this.currentUser = this._coreMenuService.currentUser;

            // Load menu
            this.menu = this._coreMenuService.getCurrentMenu();

            this._changeDetectorRef.markForCheck();
        });
    }
}
