/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {CoreCommonModule} from '@core/common.module';

import {ErrorComponent} from 'app/main/pages/miscellaneous/error/error.component';

// routing
const routes: Routes = [
    {
        path: 'error',
        component: ErrorComponent,
        data: {animation: 'misc'}
    }
];

@NgModule({
    declarations: [ErrorComponent],
    imports: [CommonModule, RouterModule.forChild(routes), CoreCommonModule]
})
export class MiscellaneousModule {
}
