import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ShiftLogsComponent} from "./components/shift-logs/shift-logs.component";
import {MachineLogsComponent} from "./components/machine-logs/machine-logs.component";
import {AccessLogsComponent} from "./components/access-logs/access-logs.component";
import {UserLogsComponent} from "./components/user-logs/user-logs.component";
import {ContainerComponent} from "./container/container.component";
import {EntryLogsComponent} from "./components/entry-logs/entry-logs.component";
import {RouterModule, Routes} from "@angular/router";
import {AppCommonModule} from "app/modules/app-common/app-common.module";
import {CoreDirectivesModule} from "@core/directives/directives";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CoreCommonModule} from "@core/common.module";
import {FormsModule} from "@angular/forms";
import {NgSelectModule} from "@ng-select/ng-select";

const routes: Routes = [
    {
        path: "list",
        component: ContainerComponent,
        canActivate: [],
        data: {animation: "logs_list"},
    },
];

@NgModule({
    declarations: [
        ContainerComponent,
        ShiftLogsComponent,
        MachineLogsComponent,
        UserLogsComponent,
        AccessLogsComponent,
        EntryLogsComponent,
    ],
    imports: [
        CommonModule,
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
