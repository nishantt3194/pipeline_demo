import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ContainerComponent} from './container/container.component';
import {AppCommonModule} from 'app/modules/app-common/app-common.module';
import {RouterModule, Routes} from '@angular/router';
import {CoreDirectivesModule} from '@core/directives/directives';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {CoreCommonModule} from '@core/common.module';
import {FormsModule} from '@angular/forms';
import {NgSelectModule} from '@ng-select/ng-select';

const routes: Routes = [
    {
        path: "area",
        component: ContainerComponent,
        canActivate: [],
        data: {animation: "machines_list"},
    },
];

@NgModule({
    declarations: [
        ContainerComponent
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
    ]
})
export class AreaModule {
}
