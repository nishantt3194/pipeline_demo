/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {CoreCommonModule} from '@core/common.module';
import {CoreSidebarModule} from '@core/components';

import {NavbarModule} from 'app/layout/components/navbar/navbar.module';
import {ContentModule} from 'app/layout/components/content/content.module';
import {MenuModule} from 'app/layout/components/menu/menu.module';
import {FooterModule} from 'app/layout/components/footer/footer.module';

import {VerticalLayoutComponent} from 'app/layout/vertical/vertical-layout.component';

@NgModule({
    declarations: [VerticalLayoutComponent],
    imports: [RouterModule, CoreCommonModule, CoreSidebarModule, NavbarModule, MenuModule, ContentModule, FooterModule],
    exports: [VerticalLayoutComponent]
})
export class VerticalLayoutModule {
}
