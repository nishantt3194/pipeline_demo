import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ContainerComponent} from './container/container.component';
import {RouterModule, Routes} from "@angular/router";
import {CoreCommonModule} from "../../../../@core/common.module";
import {AppCommonModule} from "../../app-common/app-common.module";
import {CoreDirectivesModule} from "../../../../@core/directives/directives";
import {NgbDropdownModule, NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {NgSelectModule} from "@ng-select/ng-select";
import {FormsModule} from "@angular/forms";
import {CardSnippetModule} from "../../../../@core/components/card-snippet/card-snippet.module";
import {ViewService} from "../services/view.service";
import { AssignMachine } from '../dao/users.models';
import { AssignMachineComponent } from './modals/assign-machine/assign-machine.component';
import { UnmapMachineComponent } from './modals/unmap-machine/unmap-machine.component';

const routes: Routes = [
    {
        path: 'view',
        component: ContainerComponent,
        canActivate: [],
        resolve: {
            data: ViewService
        },
        data: {animation: 'active__users__view'},
    },
];

@NgModule({
    declarations: [
        ContainerComponent,
        AssignMachineComponent,
    UnmapMachineComponent    ],
    imports: [
        CommonModule,
        CoreCommonModule,
        AppCommonModule,
        CoreDirectivesModule,
        RouterModule.forChild(routes),
        NgbModule,
        NgSelectModule,
        FormsModule,
        NgbDropdownModule,
    ]
})

export class ViewModule {
}
