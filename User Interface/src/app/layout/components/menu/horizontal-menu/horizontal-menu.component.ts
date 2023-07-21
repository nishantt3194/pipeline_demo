/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';

import {Subject} from 'rxjs';
import {filter, takeUntil} from 'rxjs/operators';

import {CoreConfigService} from '@core/services/config.service';
import {CoreMenuService} from '@core/components/core-menu/core-menu.service';
import {CoreSidebarService} from '@core/components/core-sidebar/core-sidebar.service';

@Component({
    selector: 'horizontal-menu',
    templateUrl: './horizontal-menu.component.html',
    styleUrls: ['./horizontal-menu.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class HorizontalMenuComponent implements OnInit, OnDestroy {
    coreConfig: any;
    menu: any;

    // Private
    private _unsubscribeAll: Subject<any>;

    /**
     * Constructor
     *
     * @param {CoreConfigService} _coreConfigService
     * @param {CoreMenuService} _coreMenuService
     * @param {CoreSidebarService} _coreSidebarService
     */
    constructor(
        private _coreConfigService: CoreConfigService,
        private _coreMenuService: CoreMenuService,
        private _coreSidebarService: CoreSidebarService
    ) {
        // Set the private defaults
        this._unsubscribeAll = new Subject();
    }

    // Lifecycle Hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On Init
     */
    ngOnInit(): void {
        // Subscribe config change
        this._coreConfigService.config.pipe(takeUntil(this._unsubscribeAll)).subscribe(config => {
            this.coreConfig = config;
        });

        // Get current menu
        this._coreMenuService.onMenuChanged
            .pipe(
                filter(value => value !== null),
                takeUntil(this._unsubscribeAll)
            )
            .subscribe(() => {
                this.menu = this._coreMenuService.getCurrentMenu();
            });
    }

    /**
     * On Destroy
     */
    ngOnDestroy(): void {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next();
        this._unsubscribeAll.complete();
    }
}
