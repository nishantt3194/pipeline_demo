import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContainerComponent } from './container/container.component';
import { RouterModule, Routes } from '@angular/router';
import { CoreCommonModule } from '@core/common.module';
import { AppCommonModule } from 'app/modules/app-common/app-common.module';
import { CoreDirectivesModule } from '@core/directives/directives';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { Ng2FlatpickrModule } from 'ng2-flatpickr';

const routes: Routes = [

  {
      path: 'view',
      component: ContainerComponent,
      canActivate: [],
      data: {animation: 'active__users__lists'},
  },

];

@NgModule({
  declarations: [
    ContainerComponent
  ],
  imports: [
    CommonModule,
    CoreCommonModule,
    AppCommonModule,
    CoreDirectivesModule,
    RouterModule.forChild(routes),
    NgbModule,
    NgSelectModule,
    FormsModule,Ng2FlatpickrModule

  ]
})
export class ViewModule { }
