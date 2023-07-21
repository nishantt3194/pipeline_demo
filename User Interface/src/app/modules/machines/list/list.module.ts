import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ContainerComponent} from "./container/container.component";
import {RouterModule, Routes} from "@angular/router";
import {AppCommonModule} from "app/modules/app-common/app-common.module";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CoreDirectivesModule} from "@core/directives/directives";
import {CoreCommonModule} from "@core/common.module";
import {NgSelectModule} from "@ng-select/ng-select";
import {FormsModule} from "@angular/forms";
import {ActiveComponent} from "./components/active/active.component";
import {InactiveComponent} from "./components/inactive/inactive.component";

const routes: Routes = [
    {
        path: "list",
        component: ContainerComponent,
        canActivate: [],
        data: {animation: "machines_list"},
    },
];

@NgModule({
    declarations: [ContainerComponent, ActiveComponent, InactiveComponent],
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
