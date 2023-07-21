import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";

import {AreaModule} from "./area/area.module";
import {ShiftModule} from "./shift/shift.module";
import {DowntimesModule} from "./downtimes/downtimes.module";
import {ViewReasonComponent} from "./modals/view-reason/view-reason.component";
import {HolidaysModule} from './holidays/holidays.module';
import {AppCommonModule} from "../app-common/app-common.module";
import {CoreDirectivesModule} from "../../../@core/directives/directives";
import { AddReasonComponent } from './modals/add-reason/add-reason.component';
import { MapReasonComponent } from './modals/map-reason/map-reason.component';
import { ViewShiftComponent } from './shift/modals/view-shift/view-shift.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

@NgModule({
    declarations: [
  ],
    imports: [AppCommonModule, CoreDirectivesModule, CommonModule, AreaModule, ShiftModule, DowntimesModule, HolidaysModule,  ],
})
export class SettingsModule {
}
