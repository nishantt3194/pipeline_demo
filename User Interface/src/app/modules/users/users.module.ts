import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListModule} from './list/list.module';
import {CoreCommonModule} from "../../../@core/common.module";
import {CoreDirectivesModule} from "../../../@core/directives/directives";
import {AppCommonModule} from "../app-common/app-common.module";
import {NgbDropdownModule, NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { ViewModule } from './view/view.module';


@NgModule({
    declarations: [
  
  
  
  ],
    imports: [
        CommonModule,
        CoreCommonModule,
        CoreDirectivesModule,
        AppCommonModule,
        NgbModule,
        CommonModule,
        ListModule,
        ViewModule,
    ]
})
export class UsersModule {
}
