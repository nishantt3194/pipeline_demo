import { Component, OnInit, ViewChild} from '@angular/core';
import { DateProfileGenerator } from '@fullcalendar/angular';
import { AreaSearch } from 'app/modules/settings/dao/settings.models';
import Chart from 'chart.js';
// import { single } from 'rxjs/operators';
import {
  ApexNonAxisChartSeries,
  ApexPlotOptions,
  ApexChart,
  ApexFill,
  ChartComponent,
  ApexStroke
} from "ng-apexcharts";
import { single } from 'rxjs/operators';
export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  labels: string[];
  fill:ApexFill;
  plotOptions: ApexPlotOptions;

  // Use the 'palette10' color palette
// colors: ApexCharts.getDefaultPalette('palette10')

};
// import {
//   ApexNonAxisChartSeries,
//   ApexPlotOptions,
//   ApexChart
// } from "ng-apexcharts";

@Component({
  selector: "app-admin-dashboard",
  templateUrl: "./admin-dashboard.component.html",
  styleUrls: ["./admin-dashboard.component.scss"],
})
export class AdminDashboardComponent implements OnInit {
  rejectionChart:any;
  oeeChart:any;
  oeeWeeklyChart:any;
  utilizationChart:any;
  utilizationWeeklyChart: { series: number[]; chart: { height: number; type: string; }; plotOptions: { radialBar: { hollow: { size: string; }; }; }; fill: { colors: string[]; }; labels: string[]; };
  
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  @ViewChild("chart") chart: ChartComponent;
  // oeeChart: { series: number[]; chart: { height: number; type: string;  };  fill:{colors:string;}; plotOptions: { radialBar: { hollow: { size: string; }; }; }; };
  // utilizationChart: { series: number[]; chart: { height: number; type: string;  }; fill:{colors:string;}; plotOptions: { radialBar: { hollow: { size: string; }; }; }; };
  // rejectionChart: { series: number[]; chart: { height: number; type: string;  };fill:ApexFill;  plotOptions: { radialBar: { hollow: { size: string; }; }; }; };

  single: any[];
  view: any[] = [700, 400];
  gradient: boolean = true;
  showLegend: boolean = true;
  showLabels: boolean = true;
  isDoughnut: boolean = false;
  areas: AreaSearch[];
  machines: [];
  areaId: string;
  machineId: string;

  // view: any[] = [400, 400];
  data: any[] = [
    { name: 'India', value: 75 },
   
  ];
  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C']
  };
  constructor() { 
    Object.assign(this, { single });
    this.oeeChart = {
      series: [85,],
      chart: {
        height: 150,
        type: "radialBar"
      },
      fill:{
        colors:'primary',
      },
      plotOptions: {
        radialBar: {
          hollow: {
            size: "75%"
            
          }
        }
      },
     
    };
    this.utilizationChart = {
      series: [85,],
      chart: {
        height: 150,
        type: "radialBar",
        
      },
      fill:{
        colors:'primary',
      },
      plotOptions: {
        radialBar: {
          hollow: {
            size: "75%"
          }
        }
      },
    }
    this.rejectionChart = {
      series: [80],
      chart: {
        height: 150,
        type: "radialBar",
        
      },    
      plotOptions: {
        radialBar: {
          hollow: {
            size: "75%"
          }
        }
      },
      fill:{
        colors:["#fb8500"]
      }
    }
    this.oeeWeeklyChart={
      series: [75],
      chart: {
        height: 150,
        type: "radialBar",
        
      },    
      plotOptions: {
        radialBar: {
          hollow: {
            size: "75%"
          }
        }
      },
      fill:{
        colors:["#ff0000"]
      },
      labels:[""]
    }
    this.utilizationWeeklyChart={
      series: [85],
      chart: {
        height: 150,
        type: "radialBar",
        
      },    
      plotOptions: {
        radialBar: {
          hollow: {
            size: "75%"
          }
        }
      },
      fill:{
        colors:["#00FF00"]
      },
      labels:[""]
    }
    
  }
  


  }

