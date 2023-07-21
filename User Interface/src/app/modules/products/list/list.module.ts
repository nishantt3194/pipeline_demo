import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ContainerComponent} from './container/container.component';
import {RouterModule, Routes} from '@angular/router';
import {AppCommonModule} from 'app/modules/app-common/app-common.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {CoreDirectivesModule} from '@core/directives/directives';
import {CoreCommonModule} from '@core/common.module';
import {FormsModule} from '@angular/forms';
import {NgSelectModule} from '@ng-select/ng-select';
import {OverviewComponent} from './components/overview/overview.component';
import {SettingsComponent} from './components/settings/settings.component';
import {NewTemplateComponent} from './modals/new-template/new-template.component';
import {NewProductComponent} from './modals/new-product/new-product.component';
import {EditProductComponent} from './modals/edit-product/edit-product.component';


const routes: Routes = [

    {
        path: 'list',
        component: ContainerComponent,
        canActivate: [],

        data: {animation: 'products_list'},
    },
];

@NgModule({
    declarations: [ContainerComponent,
        OverviewComponent,
        SettingsComponent,
        NewTemplateComponent,
        NewProductComponent,
        EditProductComponent],
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
export class ListModule {
}
