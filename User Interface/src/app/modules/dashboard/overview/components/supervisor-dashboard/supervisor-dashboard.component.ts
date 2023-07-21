import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { CumulativeDataRef,MachineEntryInfo } from "app/modules/dashboard/dao/dashboard.models";
import { SupervisorDashboardService } from "app/modules/dashboard/services/supervisor-dashboard.service";
import {
  EntryInfo,
  NewEntryMetadataRef,
  NewEntryRef,
} from "app/modules/entries/dao/shifts.models";
import { CreateService } from "app/modules/entries/services/create.service";
import { MachineSearch } from "app/modules/machines/dao/machines.models";
import { MachinesService } from "app/modules/machines/services/machines.service";
import { AreaSearch } from "app/modules/settings/dao/settings.models";
import { AreaService } from "app/modules/settings/services/area.service";
import {
  ApexNonAxisChartSeries,
  ApexPlotOptions,
  ApexChart,
  ApexFill,
  ChartComponent,
  ApexStroke
} from "ng-apexcharts";
import { single } from "rxjs/operators";
import { AuthenticationService } from 'app/auth/service';

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  labels: string[];
  plotOptions: ApexPlotOptions;
  fill: ApexFill;
  stroke: ApexStroke;
};

@Component({
  selector: "app-supervisor-dashboard",
  templateUrl: "./supervisor-dashboard.component.html",
  styleUrls: ["./supervisor-dashboard.component.scss"],
})
export class SupervisorDashboardComponent implements OnInit {

  currentDate:string;
  currentUser:string;

  @ViewChild("chart") chart: ChartComponent;
  oeeTopPerformingChart:any;
  rejectionTopPerformingChart:any;
  utilizationTopPerformingChart:any;
  utilizationMonthlyChart:any;
  oeeMonthlyChart:any;

  metadata: NewEntryMetadataRef;
  payload: NewEntryRef;

  single: any[];
  view: any[] = [700, 400];
  gradient: boolean = true;
  showLegend: boolean = true;
  showLabels: boolean = true;
  isDoughnut: boolean = false;
  areas: AreaSearch[];
  machines: MachineSearch[];
  areaId: string;
  topMachineArea:string;
  machineId: string;
  cumulativeData: CumulativeDataRef;

  ref: EntryInfo[];
  topMachineRef:MachineEntryInfo;

  topPerformingChart=false;
  isMonthlyChartPresent=false;
  
  constructor(
    private _auth:AuthenticationService,
    private _areaService: AreaService,
    private _createService: CreateService,
    private _machineService: MachinesService,
    private _superService: SupervisorDashboardService,
    private _route:Router,
    private _datePipe:DatePipe
    
  ) {
    Object.assign(this, { single });
   
  }

  onSelect(data): void {
    // console.log("Item clicked", JSON.parse(JSON.stringify(data)));
  }
  ngOnInit(): void {

    this.currentDate=this._datePipe.transform(new Date(),"dd-MMMM-yyyy");
    this._auth.currentUser.subscribe(res=>this.currentUser=res.firstName);

    this._areaService.search().subscribe((areas: AreaSearch[]) => {
      this.areas=areas;
      let index=this.areas.findIndex(obj=>obj.label==='IVC Assembly');
      this._superService.topPerformingMachine(areas[index].identifier).subscribe(res=>{
        this.topMachineRef=res
        this.buildOeeTopPerformingChart(res.oee);
        this.buildRejectionTopPerformingChart(res.rejectionPercentage);
        this.buildUtilizationTopPerformingChart(res.utilizationPercentage);
      });
      this.areas=areas.filter(area=>area.label!=="All");
    });
    

    this._superService.lastThreeEntries().subscribe({
      next: (value) => {
        this.ref = value;
      }
    })
  }

  navigateToEntryView(entryId:any){
    
    this._route.navigate(['/entries/view'],{
      queryParams:{
        id:entryId
      }
    });
  }

  refreshMachines(area: any) {
    if (area != null) {
      this._machineService.search(area.identifier).subscribe(
        (machines) => {
          this.machines = machines;
        }
      )
    }
  }

  buildRejectionTopPerformingChart(rejectionPercentage:number){
  
    this.rejectionTopPerformingChart={
      series: [rejectionPercentage],
      chart: {
        height: 250,
        type: "radialBar",
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        radialBar: {
          startAngle: -135,
          endAngle: 225,
          hollow: {
            margin: 0,
            size: "70%",
            background: "#fff",
            image: undefined,
            position: "front",
            dropShadow: {
              enabled: true,
              top: 3,
              left: 0,
              blur: 4,
              opacity: 0.24
            }
          },
          track: {
            background: "#fff",
            strokeWidth: "67%",
            margin: 0, // margin is in pixels
            dropShadow: {
              enabled: true,
              top: -3,
              left: 0,
              blur: 4,
              opacity: 0.35
            }
          },

          dataLabels: {
            show: true,
            name: {
              offsetY: -10,
              show: true,
              color: "#888",
              fontSize: "17px"
            },
            value: {
              formatter: function(val) {
                return val.toFixed(2).toString()+"%";
              },
              color: "#111",
              fontSize: "28px",
              show: true
            }
          }
        }
      },
      fill: {
        colors:['#ea5455']
      },
      stroke: {
        lineCap: "round"
      },
      labels: ["Rejection"]
    };
  }

  buildUtilizationTopPerformingChart(utilizationPercentage:number){
    let color=(utilizationPercentage<85)?'#ea5455':'#33963d';

    this.utilizationTopPerformingChart={
      series: [utilizationPercentage],
      chart: {
        height: 250,
        type: "radialBar",
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        radialBar: {
          startAngle: -135,
          endAngle: 225,
          hollow: {
            margin: 0,
            size: "70%",
            background: "#fff",
            image: undefined,
            position: "front",
            dropShadow: {
              enabled: true,
              top: 3,
              left: 0,
              blur: 4,
              opacity: 0.24
            }
          },
          track: {
            background: "#fff",
            strokeWidth: "67%",
            margin: 0, // margin is in pixels
            dropShadow: {
              enabled: true,
              top: -3,
              left: 0,
              blur: 4,
              opacity: 0.35
            }
          },

          dataLabels: {
            show: true,
            name: {
              offsetY: -10,
              show: true,
              color: "#888",
              fontSize: "17px"
            },
            value: {
              formatter: function(val) {
                return val.toFixed(2).toString()+"%";
              },
              color: "#111",
              fontSize: "28px",
              show: true
            }
          }
        }
      },
      fill: {
        colors:[color]
      },
      stroke: {
        lineCap: "round"
      },
      labels: ["Utilization"]
    };
  }

  buildOeeTopPerformingChart(oeePercentage:number){

    let color=(oeePercentage<85)?'#ea5455':'#33963d';

    this.oeeTopPerformingChart={
      series: [oeePercentage],
      chart: {
        height: 250,
        type: "radialBar",
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        radialBar: {
          startAngle: -135,
          endAngle: 225,
          hollow: {
            margin: 0,
            size: "70%",
            background: "#fff",
            image: undefined,
            position: "front",
            dropShadow: {
              enabled: true,
              top: 3,
              left: 0,
              blur: 4,
              opacity: 0.24
            }
          },
          track: {
            background: "#fff",
            strokeWidth: "67%",
            margin: 0, // margin is in pixels
            dropShadow: {
              enabled: true,
              top: -3,
              left: 0,
              blur: 4,
              opacity: 0.35
            }
          },

          dataLabels: {
            show: true,
            name: {
              offsetY: -10,
              show: true,
              color: "#888",
              fontSize: "17px"
            },
            value: {
              formatter: function(val) {
                return val.toFixed(2).toString()+"%";
              },
              color: "#111",
              fontSize: "28px",
              show: true
            }
          }
        }
      },
      fill: {
        colors:[color]
      },
      stroke: {
        lineCap: "round"
      },
      labels: ["OEE"]
    };
  }

  buildUtilizationMonthlyChart(utilizationPercentage:number){
    let color=(utilizationPercentage<85)?'#ea5455':'#33963d';

    this.utilizationMonthlyChart={
      series: [utilizationPercentage],
      chart: {
        height: 250,
        type: "radialBar",
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        radialBar: {
          startAngle: -135,
          endAngle: 225,
          hollow: {
            margin: 0,
            size: "70%",
            background: "#fff",
            image: undefined,
            position: "front",
            dropShadow: {
              enabled: true,
              top: 3,
              left: 0,
              blur: 4,
              opacity: 0.24
            }
          },
          track: {
            background: "#fff",
            strokeWidth: "67%",
            margin: 0, // margin is in pixels
            dropShadow: {
              enabled: true,
              top: -3,
              left: 0,
              blur: 4,
              opacity: 0.35
            }
          },

          dataLabels: {
            show: true,
            name: {
              offsetY: -10,
              show: true,
              color: "#888",
              fontSize: "17px"
            },
            value: {
              formatter: function(val) {
                return val.toFixed(2).toString()+"%";
              },
              color: "#111",
              fontSize: "28px",
              show: true
            }
          }
        }
      },
      fill: {
        colors:[color]
      },
      stroke: {
        lineCap: "round"
      },
      labels: ["Utilization"]
    };
  }

  buildOeeMonthlyChart(oeePercentage:number){

    let color=(oeePercentage<85)?'#ea5455':'#33963d';

    this.oeeMonthlyChart={
      series: [oeePercentage],
      chart: {
        height: 250,
        type: "radialBar",
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        radialBar: {
          startAngle: -135,
          endAngle: 225,
          hollow: {
            margin: 0,
            size: "70%",
            background: "#fff",
            image: undefined,
            position: "front",
            dropShadow: {
              enabled: true,
              top: 3,
              left: 0,
              blur: 4,
              opacity: 0.24
            }
          },
          track: {
            background: "#fff",
            strokeWidth: "67%",
            margin: 0, // margin is in pixels
            dropShadow: {
              enabled: true,
              top: -3,
              left: 0,
              blur: 4,
              opacity: 0.35
            }
          },

          dataLabels: {
            show: true,
            name: {
              offsetY: -10,
              show: true,
              color: "#888",
              fontSize: "17px"
            },
            value: {
              formatter: function(val) {
                return val.toFixed(2).toString()+"%";
              },
              color: "#111",
              fontSize: "28px",
              show: true
            }
          }
        }
      },
      fill: {
        colors:[color]
      },
      stroke: {
        lineCap: "round"
      },
      labels: ["OEE"]
    };
  }
  
  addNewEntry() { 
    this._route.navigate(['/entries/create']);
  }

  topMachine(){
    
    if(this.topMachineArea){
      this._superService.topPerformingMachine(this.topMachineArea).subscribe((response)=>{
        this.topMachineRef=response;
        this.buildOeeTopPerformingChart(response.oee);
        this.buildUtilizationTopPerformingChart(response.utilizationPercentage);
        this.buildRejectionTopPerformingChart(response.rejectionPercentage);
        this.topPerformingChart=true;
      });
    }
  }

  generate() {
    if (this.machineId) {
      this._superService.totalData(this.machineId).subscribe({
        next: (data) => {
          this.isMonthlyChartPresent=true;
          this.cumulativeData = data;
          this.buildOeeMonthlyChart(data.oeePercentage);
          this.buildUtilizationMonthlyChart(data.utilizationPercentage);
        },
      });
    }
  }
}
