import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ListModule } from "./list/list.module";
import { ViewModule } from "./view/view.module";
import { AppCommonModule } from "../app-common/app-common.module";
import { CoreDirectivesModule } from "../../../@core/directives/directives";

@NgModule({
  declarations: [
    // ContainerComponent
  ],
  imports: [
    AppCommonModule,
    CoreDirectivesModule,
    CommonModule,
    ListModule,
    ViewModule,
  ],
})
export class MachinesModule {}
