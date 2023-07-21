import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ContainerComponent} from './container/container.component';
import {DetailsComponent} from './components/details/details.component';
import {DowntimesComponent} from './components/downtimes/downtimes.component';
import {ProductionComponent} from './components/production/production.component';
import {RemarksComponent} from './components/remarks/remarks.component';
import {RouterModule, Routes} from '@angular/router';
import {NgSelectModule} from '@ng-select/ng-select';
import {CoreCommonModule} from '@core/common.module';
import {CoreDirectivesModule} from '@core/directives/directives';
import {AppCommonModule} from 'app/modules/app-common/app-common.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {CardSnippetModule} from '@core/components/card-snippet/card-snippet.module';
import {FormsModule} from '@angular/forms';
import {NouisliderModule} from 'ng2-nouislider';
import {AddDowntimeComponent} from './modals/add-downtime/add-downtime.component';
import {CoreSidebarModule} from '@core/components';
import {AddPrechecksComponent} from './modals/add-prechecks/add-prechecks.component';
import {CreateService} from "../services/create.service";
import {RequirementPromptComponent} from './modals/requirement-prompt/requirement-prompt.component';
import {SweetAlert2Module} from "@sweetalert2/ngx-sweetalert2";
import {SubmissionConfirmationComponent} from './modals/submission-confirmation/submission-confirmation.component';


const routes: Routes = [
    {
        path: "create",
        component: ContainerComponent,
        canActivate: [],
        resolve: {
            data: CreateService
        },
        data: {animation: "shifts_create"},
    },
    {
        path: "edit",
        component: ContainerComponent,
        canActivate: [],
        resolve: {
            data: CreateService
        },
        data: {animation: "shifts_edit"},
    }
];

@NgModule({
    declarations: [
        ContainerComponent,
        DetailsComponent,
        DowntimesComponent,
        ProductionComponent,
        RemarksComponent,
        AddDowntimeComponent,
        AddPrechecksComponent,
        RequirementPromptComponent,
        SubmissionConfirmationComponent
    ],
    imports: [
        CommonModule,
        NgSelectModule,
        CoreCommonModule,
        CoreDirectivesModule,
        RouterModule.forChild(routes),
        AppCommonModule,
        NgbModule,
        CardSnippetModule,
        FormsModule,
        NouisliderModule,
        CoreSidebarModule,
        NgSelectModule,
        SweetAlert2Module,
    ],
    exports: [RouterModule],
})
export class CreateModule {
}
