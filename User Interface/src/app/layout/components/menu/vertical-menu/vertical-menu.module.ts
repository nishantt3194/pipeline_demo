/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule} from 'ngx-perfect-scrollbar';

import {CoreMenuModule} from '@core/components';
import {CoreCommonModule} from '@core/common.module';

import {VerticalMenuComponent} from 'app/layout/components/menu/vertical-menu/vertical-menu.component';
import {AppCommonModule} from "../../../../modules/app-common/app-common.module";

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
    suppressScrollX: true,
    wheelPropagation: false
};

@NgModule({
    declarations: [VerticalMenuComponent],
    imports: [CoreMenuModule, CoreCommonModule, PerfectScrollbarModule, RouterModule, AppCommonModule],
    providers: [
        {
            provide: PERFECT_SCROLLBAR_CONFIG,
            useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
        }
    ],
    exports: [VerticalMenuComponent]
})
export class VerticalMenuModule {
}
