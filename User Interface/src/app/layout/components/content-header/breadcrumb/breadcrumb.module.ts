/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';

import {BreadcrumbComponent} from 'app/layout/components/content-header/breadcrumb/breadcrumb.component';

@NgModule({
    declarations: [BreadcrumbComponent],
    imports: [CommonModule, RouterModule.forChild([])],
    exports: [BreadcrumbComponent]
})
export class BreadcrumbModule {
}
