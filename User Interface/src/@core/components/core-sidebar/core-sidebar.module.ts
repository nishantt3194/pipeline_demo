/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {NgModule} from '@angular/core';

import {CoreSidebarComponent} from '@core/components/core-sidebar/core-sidebar.component';

@NgModule({
    declarations: [CoreSidebarComponent],
    exports: [CoreSidebarComponent]
})
export class CoreSidebarModule {
}
