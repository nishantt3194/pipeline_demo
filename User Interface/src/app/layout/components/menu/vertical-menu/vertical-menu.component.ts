/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';

import {Subject} from 'rxjs';
import {filter, take, takeUntil} from 'rxjs/operators';
import {PerfectScrollbarDirective} from 'ngx-perfect-scrollbar';

import {CoreConfigService} from '@core/services/config.service';
import {CoreMenuService} from '@core/components/core-menu/core-menu.service';
import {CoreSidebarService} from '@core/components/core-sidebar/core-sidebar.service';
import {LoggedInUser} from '../../../../auth/models';
import {AuthenticationService} from '../../../../auth/service';
import {animate, style, transition, trigger} from '@angular/animations';

@Component({
    selector: 'vertical-menu',
    templateUrl: './vertical-menu.component.html',
    styleUrls: ['./vertical-menu.component.scss'],
    encapsulation: ViewEncapsulation.None,
    animations: [
        trigger(
            'enterAnimation', [
                transition(':enter', [
                    style({opacity: 0}),
                    animate('500ms', style({opacity: 1}))
                ]),
                transition(':leave', [
                    style({opacity: 1}),
                    animate('500ms', style({opacity: 0}))
                ])
            ]
        )
    ],
})
export class VerticalMenuComponent implements OnInit, OnDestroy {
    coreConfig: any;
    menu: any;
    isCollapsed: boolean;
    isExpanded: boolean;
    isScrolled: boolean = false;
    @ViewChild(PerfectScrollbarDirective, {static: false}) directiveRef?: PerfectScrollbarDirective;
    public currentUser: LoggedInUser;
    // Private
    private _unsubscribeAll: Subject<any>;

    /**
     * Constructor
     *
     * @param {CoreConfigService} _coreConfigService
     * @param {CoreMenuService} _coreMenuService
     * @param {CoreSidebarService} _coreSidebarService
     * @param {Router} _router
     * @param {AuthenticationService} _authenticationService
     */
    constructor(
        private _coreConfigService: CoreConfigService,
        private _coreMenuService: CoreMenuService,
        private _coreSidebarService: CoreSidebarService,
        private _router: Router,
        private _authenticationService: AuthenticationService,
    ) {
        // Set the private defaults
        this._unsubscribeAll = new Subject();
        this._authenticationService.currentUser.subscribe(user => {
            this.currentUser = user;
        });
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

        this.isExpanded = this._coreSidebarService.getSidebarRegistry('menu').expanded;

        this.isCollapsed = this._coreSidebarService.getSidebarRegistry('menu').collapsed;

        // Close the menu on router NavigationEnd (Required for small screen to close the menu on select)
        this._router.events
            .pipe(
                filter(event => event instanceof NavigationEnd),
                takeUntil(this._unsubscribeAll)
            )
            .subscribe(() => {
                if (this._coreSidebarService.getSidebarRegistry('menu')) {
                    this._coreSidebarService.getSidebarRegistry('menu').close();
                }
            });

        // scroll to active on navigation end
        this._router.events
            .pipe(
                filter(event => event instanceof NavigationEnd),
                take(1)
            )
            .subscribe(() => {
                setTimeout(() => {
                    this.directiveRef.scrollToElement('.navigation .active', -180, 500);
                });
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

        this._coreSidebarService.getSidebarRegistry('menu').collapsedChangedEvent.subscribe({
            next: () => {
                this.isExpanded = this._coreSidebarService.getSidebarRegistry('menu').expanded;
            }
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

    // Public Methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * On Sidebar scroll set isScrolled as true
     */
    onSidebarScroll(): void {
        if (this.directiveRef.position(true).y > 3) {
            this.isScrolled = true;
        } else {
            this.isScrolled = false;
        }
    }

    /**
     * Toggle sidebar expanded status
     */
    toggleSidebar(): void {
        this._coreSidebarService.getSidebarRegistry('menu').toggleOpen();
    }

    /**
     * Toggle sidebar collapsed status
     */
    toggleSidebarCollapsible(): void {
        // Get the current menu state
        this._coreConfigService
            .getConfig()
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe(config => {
                this.isCollapsed = config.layout.menu.collapsed;
            });

        if (this.isCollapsed) {
            this._coreConfigService.setConfig({layout: {menu: {collapsed: false}}}, {emitEvent: true});
        } else {
            this._coreConfigService.setConfig({layout: {menu: {collapsed: true}}}, {emitEvent: true});
        }
    }

    logout() {
        this._authenticationService.logout();
    }
}
