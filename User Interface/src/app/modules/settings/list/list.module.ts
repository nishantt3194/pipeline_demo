import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule, Routes} from "@angular/router";
import {ContainerComponent} from "./container/container.component";
import {ShiftsComponent} from "./components/shifts/shifts.component";
import {DowntimesComponent} from "./components/downtimes/downtimes.component";
import {AreasComponent} from "./components/areas/areas.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {AppCommonModule} from "app/modules/app-common/app-common.module";
import {CoreCommonModule} from "@core/common.module";
import {CoreDirectivesModule} from "@core/directives/directives";
import {NgSelectModule} from "@ng-select/ng-select";
import {FormsModule} from "@angular/forms";
import {NewShiftComponent} from '../shift/modals/new-shift/new-shift.component';
import {NewAreaComponent} from '../modals/new-area/new-area.component';
import {EditShiftComponent} from '../shift/modals/edit-shift/edit-shift.component';

const routes: Routes = [
    {
        path: "list",
        component: ContainerComponent,
        canActivate: [],
        data: {animation: "machines_list"},
    },
];

@NgModule({
    declarations: [
        ContainerComponent,
        ShiftsComponent,
        AreasComponent,
        DowntimesComponent,
        NewShiftComponent,
        NewAreaComponent,
        EditShiftComponent,
    ],
    imports: [CommonModule,
        AppCommonModule,
        RouterModule.forChild(routes),
        NgbModule,
        CoreDirectivesModule,
        CoreCommonModule,

        FormsModule,
        NgSelectModule,
    ],
})
export class ListModule {
}
