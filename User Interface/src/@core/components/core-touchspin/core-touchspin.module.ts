/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {CoreTouchspinComponent} from '@core/components/core-touchspin/core-touchspin.component';
import {CoreCommonModule} from '@core/common.module';

@NgModule({
    declarations: [CoreTouchspinComponent],
    imports: [CommonModule, FormsModule, CoreCommonModule],
    exports: [CoreTouchspinComponent]
})
export class CoreTouchspinModule {
}
