import { DatePipe } from "@angular/common";
import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { Router } from "@angular/router";
import { MachinesService } from "app/modules/machines/services/machines.service";
import { AreaSearch, MachineSearch } from "app/modules/settings/dao/settings.models";
import { AreaService } from "app/modules/settings/services/area.service";
import { FlatpickrOptions } from "ng2-flatpickr";
import {Chart} from "chart.js"

@Component({
  selector: "app-container",
  templateUrl: "./container.component.html",
  styleUrls: ["./container.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class ContainerComponent implements OnInit {
  start: Date;
  end: Date;
  areaId: string;
  machineId: string;
  areas: AreaSearch[];
  machines: MachineSearch[];
  constructor(private _router: Router, private _datePipe: DatePipe, private _areaService: AreaService, private _machineService: MachinesService) {}

  public rangeOptions: FlatpickrOptions = {
    altInput: true,
    mode: "range",
    defaultDate: new Date(),
  };

  generate() {
    let start = this._datePipe.transform(this.start, "dd-MM-yyyy");
    let end = this._datePipe.transform(this.end, "dd-MM-yyyy");
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
    this._areaService.search().subscribe((areas: AreaSearch[]) => {
        this.areas = areas;
    });
    this._machineService.search().subscribe((machines: MachineSearch[])=> {
        this.machines = machines;
    });



      
  }
}
