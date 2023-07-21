/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {NgModule} from '@angular/core';
import {FlexLayoutModule} from '@angular/flex-layout';

import {CustomBreakPointsProvider} from 'app/layout/custom-breakpoints';
import {VerticalLayoutModule} from 'app/layout/vertical/vertical-layout.module';
import {HorizontalLayoutModule} from 'app/layout/horizontal/horizontal-layout.module';

@NgModule({
    imports: [FlexLayoutModule.withConfig({disableDefaultBps: true}), VerticalLayoutModule, HorizontalLayoutModule],
    providers: [CustomBreakPointsProvider],
    exports: [VerticalLayoutModule, HorizontalLayoutModule]
})
export class LayoutModule {
}
