import { Component, OnInit } from '@angular/core';
import { HeaderItem, Page } from 'app/modules/app-common/models/app-common.models';
import { Observable, Subject } from 'rxjs';
import { EntryInfo, ProductionTableRef } from '../../dao/shifts.models';
import { ProductionHeaders } from '../../dao/shifts.headers';
import { ViewService } from '../../services/view.service';
import { ActivatedRoute, Router} from '@angular/router';
import { AuthenticationService } from 'app/auth/service';

@Component({
  selector: 'app-container',
  templateUrl: './container.component.html',
  styleUrls: ['./container.component.scss']
})
export class ContainerComponent implements OnInit {
  private _unsubscribeAll: Subject<any>;
  headers: HeaderItem[];
  ref:EntryInfo;
  data:any;
  entryId:string;
  role:string;
  constructor(private viewService:ViewService,
    private route:ActivatedRoute,
    private router:Router, private authService: AuthenticationService) {

    this.headers=ProductionHeaders;
    this._unsubscribeAll=new Subject();
       
    this.viewService.onEntryChange.subscribe({
      next: (entryInfo) => {
        this.ref = entryInfo;
      },
    });
    
    this.route.queryParams.subscribe(params=>{
      this.entryId=params.id;
    });


  }

  ngOnInit(): void {
    
  }

  routeToCreate(){
    this.router.navigate(['/entries/edit'],
     {queryParams: 
        {
          id: this.entryId,
          saveType:"EDIT"
        }
      });
    }
  }
