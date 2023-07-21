import { Component, OnInit } from '@angular/core';
import { CoreSidebarService } from '@core/components/core-sidebar/core-sidebar.service';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { NewHolidaysPayload } from 'app/modules/settings/dao/settings.models';
import { HolidaysService } from 'app/modules/settings/services/holidays.service';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.scss']
})
export class AddEventComponent implements OnInit {

  public basicDPdata: NgbDateStruct;
  public holidayTitle:string;
  public holidayDesc:string;

  constructor(private _coreSideBarService:CoreSidebarService,
    private _holidayService:HolidaysService
    ) { }

  ngOnInit(): void {
  }

  toggleSidebar(){
    this._coreSideBarService.getSidebarRegistry("add-event").toggleOpen()
  }

  saveHoliday(){
    
    let str=this.basicDPdata;
    let newDate=`${str.year}-${str.month.toString().length==1?('0'+str.month):str.month}-${str.day}`
    let payload:NewHolidaysPayload ={
      name:this.holidayTitle,
      desc:this.holidayDesc,
      date:newDate
    } 
    this._holidayService.createNewHoliday(payload).subscribe((data)=>console.log(data));
    console.log(payload)
    this.toggleSidebar();
  }

}
