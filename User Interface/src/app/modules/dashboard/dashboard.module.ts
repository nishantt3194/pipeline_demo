import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { OverviewModule } from "./overview/overview.module";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";




@NgModule({
  declarations: [] ,
  imports: [CommonModule, OverviewModule, NgbModule],
})
export class DashboardModule {}
