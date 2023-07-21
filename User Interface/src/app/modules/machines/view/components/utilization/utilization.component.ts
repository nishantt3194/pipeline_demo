import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MachinesService } from 'app/modules/machines/services/machines.service';
import { ViewService } from 'app/modules/machines/services/view.service';
import { AreaSearch, MachineSearch } from 'app/modules/settings/dao/settings.models';
import { AreaService } from 'app/modules/settings/services/area.service';
import Chart from 'chart.js';
import { FlatpickrOptions } from 'ng2-flatpickr';

@Component({
  selector: 'app-utilization',
  templateUrl: './utilization.component.html',
  styleUrls: ['./utilization.component.scss']
})
export class UtilizationComponent implements OnInit {
 
  start: Date;
  end: Date;
  areaId: string;
  machineId: string;
  
  
  constructor(private _router: Router, private _datePipe: DatePipe, private _areaService: AreaService, private _viewService: ViewService,
    private _route:ActivatedRoute) {
      this._route.queryParams.subscribe((machine)=>this.machineId=machine.id);
    }

  public rangeOptions: FlatpickrOptions = {
    altInput: true,
    mode: "range",
    defaultDate: new Date(),
  };

  generate() {
    let start = this._datePipe.transform(this.start, "dd-MM-yyyy");
    let end = this._datePipe.transform(this.end, "dd-MM-yyyy");
    console.log(start,'----',end,'---',this.machineId);
    this._viewService.utilizationPercentageForRange(this.machineId,start,end).subscribe((data)=>console.log(data));
    
    const machineNames = ['Machine 1', 'Machine 2', 'Machine 3', 'Machine 4'];
      const productionData = [200, 300, 150, 250];
      const rejectionData = [20, 50, 10, 30];
    
      const chart = new Chart('chart', {
        type: 'line',
        data: {
          labels: machineNames,
          datasets: [
            {
              label: 'Production',
              data: productionData,
              borderColor: 'blue',
              fill: false
            },
            {
              label: 'Rejection',
              data: rejectionData,
              borderColor: 'red',
              fill: false
            }
          ]
        },
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              }
            }]
          }
        }
      });
    
  }
  setDates($event: Date[]) {
    this.start = $event[0];
    this.end = $event[1];
  }

  ngOnInit(): void {
    
  }
}
