/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TableBuilderComponent} from './components/table-builder/table-builder.component';
import {NgxDatatableModule} from "@swimlane/ngx-datatable";
import {CsvModule} from "@ctrl/ngx-csv";
import {CoreDirectivesModule} from "../../../@core/directives/directives";
import {CoreCommonModule} from "../../../@core/common.module";
import {FormsModule} from "@angular/forms";
import {NgbDropdownModule, NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {RouterModule} from "@angular/router";
import {NgSelectModule} from "@ng-select/ng-select";
import {AvailabilityDirective} from './directives/availability.directive';
import {CreditTrimPipe} from "./pipes/credit-trim.pipe";
import {NgxExtendedPdfViewerModule} from 'ngx-extended-pdf-viewer';
import {LoaderComponent} from './components/loader/loader.component';
import {SubmitBlockerComponent} from './components/submit-blocker/submit-blocker.component';
import {ErrorDialogComponent} from './modals/error-dialog/error-dialog.component';
import {CoreSolarIconsDirective} from './directives/core-solar-icons.directive';
import {ScreenHeaderComponent} from './components/screen-header/screen-header.component';

@NgModule({
    declarations: [
        TableBuilderComponent,
        AvailabilityDirective,
        CreditTrimPipe,
        LoaderComponent,
        SubmitBlockerComponent,
        ErrorDialogComponent,
        CoreSolarIconsDirective,
        ScreenHeaderComponent,
    ],
    exports: [
        TableBuilderComponent,
        AvailabilityDirective,
        CreditTrimPipe,
        LoaderComponent,
        SubmitBlockerComponent,
        CoreSolarIconsDirective,
        ScreenHeaderComponent
    ],
    imports: [
        CommonModule,
        NgbModule,
        CoreDirectivesModule,
        CoreCommonModule,
        FormsModule,
        NgxDatatableModule,
        CsvModule,
        RouterModule,
        NgSelectModule,
        NgxExtendedPdfViewerModule,
        NgbDropdownModule,
    ]
})
export class AppCommonModule {
}
