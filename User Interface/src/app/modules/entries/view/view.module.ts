import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ContainerComponent } from './container/container.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { CoreDirectivesModule } from '@core/directives/directives';
import { CoreCommonModule } from '@core/common.module';
import { AppCommonModule } from 'app/modules/app-common/app-common.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CardSnippetModule } from '@core/components/card-snippet/card-snippet.module';
import { FormsModule } from '@angular/forms';
import { Ng2FlatpickrModule } from 'ng2-flatpickr';
import { ViewService } from '../services/view.service';

const routes: Routes = [
  {
      path: "view",
      component: ContainerComponent,
      canActivate: [],
      resolve: {
        prefetched: ViewService
      },
      data: {animation: "shifts_list"},
  },
];

@NgModule({
  declarations: [ContainerComponent],
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
    Ng2FlatpickrModule,  ]
})
export class ViewModule { }
