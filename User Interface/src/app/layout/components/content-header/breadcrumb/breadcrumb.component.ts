/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Component, Input, OnInit} from '@angular/core';

// Breadcrumb component interface
export interface Breadcrumb {
    type?: string;
    alignment?: string;
    links?: Array<{
        name: string;
        isLink: boolean;
        link?: string;
    }>;
}

@Component({
    selector: 'app-breadcrumb',
    templateUrl: './breadcrumb.component.html'
})
export class BreadcrumbComponent implements OnInit {
    // input variable
    @Input() breadcrumb: Breadcrumb;

    constructor() {
    }

    // Lifecycle Hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit() {
        // concatenate default properties with passed properties
        this.breadcrumb = this.breadcrumb;
    }
}
