import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ContainerComponent } from "./container/container.component";
import { AppCommonModule } from "app/modules/app-common/app-common.module";
import { RouterModule, Routes } from "@angular/router";
import { CoreDirectivesModule } from "@core/directives/directives";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { CoreCommonModule } from "@core/common.module";
import { FormsModule } from "@angular/forms";
import { NgSelectModule } from "@ng-select/ng-select";
import { NewShiftComponent } from "./modals/new-shift/new-shift.component";
import { Ng2FlatpickrModule } from "ng2-flatpickr";
import { ViewShiftComponent } from "./modals/view-shift/view-shift.component";
import { EditShiftComponent } from "./modals/edit-shift/edit-shift.component";

const routes: Routes = [
  {
    path: "shift",
    component: ContainerComponent,
    canActivate: [],
    data: { animation: "machines_list" },
  },
];

@NgModule({
  declarations: [ContainerComponent, NewShiftComponent, ViewShiftComponent, EditShiftComponent],
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
export class ShiftModule {}
