import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ContainerComponent} from './container/container.component';
import {ActiveUsersComponent} from './components/active-users/active-users.component';
import {InactiveUsersComponent} from './components/inactive-users/inactive-users.component';
import {CoreCommonModule} from "../../../../@core/common.module";
import {AppCommonModule} from "../../app-common/app-common.module";
import {CoreDirectivesModule} from "../../../../@core/directives/directives";
import {RouterModule, Routes} from "@angular/router";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {OperatorComponent} from './components/operator/operator.component';
import {SupervisorComponent} from './components/supervisor/supervisor.component';
import {NgSelectModule} from '@ng-select/ng-select';
import {FormsModule} from '@angular/forms';
import {ViewSupervisorComponent} from './modals/view-supervisor/view-supervisor.component';
import {ViewOperatorComponent} from './modals/view-operator/view-operator.component';
import {ViewComponent} from './modals/view/view.component';
import {AddUserComponent} from './modals/add-user/add-user.component';
import {AssignMachineComponent} from '../view/modals/assign-machine/assign-machine.component';

const routes: Routes = [
    {
        path: 'list',
        component: ContainerComponent,
        canActivate: [],
        data: {animation: 'active__users__lists'},
    },
];

@NgModule({
    declarations: [
        ContainerComponent,
        ActiveUsersComponent,
        InactiveUsersComponent,
        OperatorComponent,
        SupervisorComponent,
        ViewSupervisorComponent,
        ViewOperatorComponent,
        ViewComponent,
        AddUserComponent,
        


    ],
    imports: [
        CommonModule,
        CoreCommonModule,
        AppCommonModule,
        CoreDirectivesModule,
        RouterModule.forChild(routes),
        NgbModule,
        NgSelectModule,
        FormsModule,
    ]
})

export class ListModule {
}
