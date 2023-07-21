import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ContainerComponent} from './container/container.component';
import {RouterModule, Routes} from "@angular/router";
import {AppCommonModule} from "../../app-common/app-common.module";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CoreDirectivesModule} from "../../../../@core/directives/directives";
import {CoreCommonModule} from "../../../../@core/common.module";
import {FormsModule} from "@angular/forms";
import {NgSelectModule} from "@ng-select/ng-select";
import {FullCalendarModule} from "@fullcalendar/angular";

import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import listPlugin from '@fullcalendar/list';
import timeGridPlugin from '@fullcalendar/timegrid';
import { AddEventComponent } from './modals/add-event/add-event.component';
import { CoreSidebarModule } from "../../../../@core/components/core-sidebar/core-sidebar.module";

FullCalendarModule.registerPlugins([dayGridPlugin, timeGridPlugin, listPlugin, interactionPlugin]);

const routes: Routes = [
    {
        path: "holidays",
        component: ContainerComponent,
        canActivate: [],
        data: {animation: "holidays_planner"},
    },
];

@NgModule({
    declarations: [
        ContainerComponent,
        AddEventComponent
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
        FullCalendarModule,
        CoreSidebarModule
    ]
})
export class HolidaysModule {
}
