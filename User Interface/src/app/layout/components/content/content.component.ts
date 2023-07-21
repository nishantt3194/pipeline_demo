/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Component, ViewEncapsulation} from '@angular/core';
import {CoreConfigService} from '@core/services/config.service';
import {fadeIn, fadeInLeft, zoomIn} from '@core/animations/core.animation';
import {Observable, Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {AuthenticationService} from "../../../auth/service";

@Component({
    selector: 'content',
    templateUrl: './content.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: [fadeInLeft, zoomIn, fadeIn]
})
export class ContentComponent {
    public coreConfig: any;
    public animate;
    public isLoggedIn: Observable<boolean>;

    // Private
    private _unsubscribeAll: Subject<any>;

    /**
     * Constructor
     *
     * @param {CoreConfigService} _coreConfigService
     *
     * @param _authenticationService
     */
    constructor(private _coreConfigService: CoreConfigService,
                private _authenticationService: AuthenticationService) {
        // Set the private defaults
        this._unsubscribeAll = new Subject();
        this.isLoggedIn = this._authenticationService.is_logged_in;
    }

    /**
     * Fade In Left Animation
     *
     * @param outlet
     */
    fadeInLeft(outlet) {
        if (this.animate === 'fadeInLeft') {
            return outlet.activatedRouteData.animation;
        }
        return null;
    }

    /**
     * Zoom In Animation
     *
     * @param outlet
     */
    zoomIn(outlet) {
        if (this.animate === 'zoomIn') {
            return outlet.activatedRouteData.animation;
        }
        return null;
    }

    /**
     * Fade In Animation
     *
     * @param outlet
     */
    fadeIn(outlet) {
        if (this.animate === 'fadeIn') {
            return outlet.activatedRouteData.animation;
        }
        return null;
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
            this.animate = this.coreConfig.layout.animation;
        });
    }
}
