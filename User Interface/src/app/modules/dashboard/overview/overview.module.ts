import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule, Routes } from "@angular/router";
import { ContainerComponent } from "./container/container.component";
import { AppCommonModule } from "app/modules/app-common/app-common.module";
import { CoreCommonModule } from "@core/common.module";
import { NgSelectModule } from "@ng-select/ng-select";
import { Chart } from "chart.js";
import { AdminDashboardComponent } from "./components/admin-dashboard/admin-dashboard.component";
import { SupervisorDashboardComponent } from "./components/supervisor-dashboard/supervisor-dashboard.component";
import { OperatorDashboardComponent } from "./components/operator-dashboard/operator-dashboard.component";
// import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// import { MatSelectModule } from '@angular/material/select';
// import { NgxChartsModule } from '@swimlane/ngx-charts';
import { AppComponent } from "app/app.component";
import { BrowserModule } from "@angular/platform-browser";
import { NgApexchartsModule } from "ng-apexcharts";

const routes: Routes = [
  {
    path: "",
    redirectTo: "overview"
  },
  {
    path: "overview",
    component: ContainerComponent,
    canActivate: [],
    data: {
      animation: "dashboard",
    },
  },
];
// @NgModule({
//   declarations: [AppComponent],
//   imports: [BrowserModule, NgApexchartsModule],
//   providers: [],
//   bootstrap: [AppComponent]
// })
@NgModule({
  declarations: [
    ContainerComponent,
    AdminDashboardComponent,
    SupervisorDashboardComponent,
    OperatorDashboardComponent,
    

  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),

    AppCommonModule,
    CoreCommonModule,
    NgSelectModule,
    NgApexchartsModule,
    // NgApexchartsModule
    // MatSelectModule,
    // BrowserAnimationsModule,

  ],
})
export class OverviewModule {}



