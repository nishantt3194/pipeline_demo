import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ContainerComponent} from "./container/container.component";
import {RouterModule, Routes} from "@angular/router";
import {NgSelectModule} from "@ng-select/ng-select";
import {CoreCommonModule} from "@core/common.module";
import {CoreDirectivesModule} from "@core/directives/directives";
import {AppCommonModule} from "app/modules/app-common/app-common.module";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CardSnippetModule} from "@core/components/card-snippet/card-snippet.module";
import {FormsModule} from "@angular/forms";
import {OverviewComponent} from "./components/overview/overview.component";
import {ReportsComponent} from "./components/reports/reports.component";
import {ViewComponent} from "./components/view/view.component";
import {PendingComponent} from "./components/pending/pending.component";
import {Ng2FlatpickrModule} from "ng2-flatpickr";
import { ZllReportComponent } from './components/zll-report/zll-report.component';

const routes: Routes = [
    {
        path: "list",
        component: ContainerComponent,
        canActivate: [],

        data: {animation: "shifts_list"},
    },
];

@NgModule({
    declarations: [ContainerComponent, OverviewComponent, ReportsComponent, ViewComponent, PendingComponent, ZllReportComponent],
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
        Ng2FlatpickrModule,
    ],
})
export class ListModule {
}
