import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ContainerComponent } from "./container/container.component";
import { RouterModule, Routes } from "@angular/router";
import { AppCommonModule } from "app/modules/app-common/app-common.module";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { CoreDirectivesModule } from "@core/directives/directives";
import { CoreCommonModule } from "@core/common.module";
import { FormsModule } from "@angular/forms";
import { NgSelectModule } from "@ng-select/ng-select";
import { OverviewComponent } from "./components/overview/overview.component";
import { AnalysisComponent } from "./components/analysis/analysis.component";
import { ShiftsHistoryComponent } from "./components/shifts-history/shifts-history.component";
import { ViewService } from "../services/view.service";
import { Ng2FlatpickrModule } from "ng2-flatpickr";
import { FilterPipe } from "@core/pipes/filter.pipe";
import { UtilizationComponent } from "./components/utilization/utilization.component";
import { EditViewComponent } from "./modals/edit-view/edit-view.component";
import { EditStationsComponent } from "./modals/edit-stations/edit-stations.component";
import { EditPrecheckComponent } from "./modals/edit-precheck/edit-precheck.component";

const routes: Routes = [
  {
    path: "view",
    component: ContainerComponent,
    canActivate: [],
    resolve: {
      prefetched: ViewService,
    },
    data: { animation: "machines_view" },
  },
];

@NgModule({
  declarations: [
    ContainerComponent,
    OverviewComponent,
    AnalysisComponent,
    ShiftsHistoryComponent,
    UtilizationComponent,
    EditViewComponent,
    EditPrecheckComponent,
    EditStationsComponent,
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
    Ng2FlatpickrModule,
  ],
})
export class ViewModule {}
