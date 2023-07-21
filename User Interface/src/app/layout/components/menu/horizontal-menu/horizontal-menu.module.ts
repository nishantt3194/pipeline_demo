/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {NgModule} from '@angular/core';

import {CoreMenuModule} from '@core/components';
import {CoreCommonModule} from '@core/common.module';

import {HorizontalMenuComponent} from 'app/layout/components/menu/horizontal-menu/horizontal-menu.component';

@NgModule({
    declarations: [HorizontalMenuComponent],
    imports: [CoreMenuModule, CoreCommonModule],
    exports: [HorizontalMenuComponent]
})
export class HorizontalMenuModule {
}
