/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {CoreCommonModule} from '@core/common.module';

import {ContentComponent} from 'app/layout/components/content/content.component';
import {AppCommonModule} from "../../../modules/app-common/app-common.module";

@NgModule({
    declarations: [ContentComponent],
    imports: [RouterModule, CoreCommonModule, AppCommonModule],
    exports: [ContentComponent]
})
export class ContentModule {
}
