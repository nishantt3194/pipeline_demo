import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ContainerComponent} from "./container/container.component";

import {AppCommonModule} from "app/modules/app-common/app-common.module";
import {RouterModule, Routes} from "@angular/router";
import {CoreDirectivesModule} from "@core/directives/directives";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CoreCommonModule} from "@core/common.module";
import {FormsModule} from "@angular/forms";
import {NgSelectModule} from "@ng-select/ng-select";
import { AddReasonComponent } from "../modals/add-reason/add-reason.component";
import { MapReasonComponent } from "../modals/map-reason/map-reason.component";
import { CategoryService } from "../services/category.service";
import { SweetAlert2Module } from "@sweetalert2/ngx-sweetalert2";


const routes: Routes = [
    {
        path: "downtimes",
        component: ContainerComponent,
        canActivate: [],
        resolve: {
            data: CategoryService
        },
        data: {animation: "machines_list"},
    },
];

@NgModule({
    declarations: [
        ContainerComponent,
        AddReasonComponent,
        MapReasonComponent
        
    ],
    imports: [CommonModule,
        AppCommonModule,
        RouterModule.forChild(routes),
        NgbModule,
        CoreDirectivesModule,
        CoreCommonModule,
        FormsModule,
        NgSelectModule,
        SweetAlert2Module
    ],
})
export class DowntimesModule {
}
