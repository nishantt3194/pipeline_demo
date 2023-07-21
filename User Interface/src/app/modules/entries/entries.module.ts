import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListModule} from './list/list.module';
import {CreateModule} from './create/create.module';
import {ShiftModule} from '../settings/shift/shift.module';
import {AreaModule} from '../settings/area/area.module';
import {DowntimesModule} from '../settings/downtimes/downtimes.module';
import { ViewModule } from './view/view.module';
import { ContainerComponent } from './view/container/container.component';


@NgModule({
    declarations: [
  ],
    imports: [
        CommonModule,
        ListModule,
        CreateModule,
        ShiftModule,
        AreaModule,
        DowntimesModule,
        ViewModule
    ]
})
export class EntriesModule {
}
