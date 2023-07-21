import { DatePipe } from "@angular/common";
import { AfterViewInit, Component, Input, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { NgbPanelChangeEvent } from "@ng-bootstrap/ng-bootstrap";
import { NgbDateStruct } from "@ng-bootstrap/ng-bootstrap/datepicker/ngb-date-struct";
import {
  BOSData,
  OeeData,
  OeeParetoData,
  ParetoData,
} from "app/modules/machines/dao/machines.models";
import { ChartService } from "app/modules/machines/services/chart.service";
import { MachinesService } from "app/modules/machines/services/machines.service";
import Chart from "chart.js";
import { FlatpickrOptions } from "ng2-flatpickr";
import { BehaviorSubject, Subject } from "rxjs";
import html2canvas from "html2canvas";

@Component({
  selector: "app-analysis",
  templateUrl: "./analysis.component.html",
  styleUrls: ["./analysis.component.scss"],
})
export class AnalysisComponent implements OnInit, AfterViewInit {


@Input('name') headerName:string;
  @ViewChild("bos") bosElement: any;
  @ViewChild("pareto") paretoElement: any;
  @ViewChild("oee") oeeElement: any;
  @ViewChild("oeepareto") oeeParetoElement: any;


  machineName:string;
  public basicDPdata: NgbDateStruct;
  bosData!: BOSData;
  paretoTable: Subject<ParetoData> = new BehaviorSubject<ParetoData>(
    new (class implements ParetoData {
      cumulativePercentage: number[] = [];
      downtime: number[] = [];
      percentage: number[] = [];
      reasons: string[] = [];
      shifts: number = 0;
    })()
  );
  oeeParetoTable: Subject<OeeParetoData> = new BehaviorSubject<OeeParetoData>(
    new (class implements OeeParetoData {
      cumulativePercentage: number[] = [];
      downtime: number[] = [];
      percentage: number[] = [];
      category: string[] = [];
      shifts: number = 0;
    })()
  );

  paretoData!: ParetoData;
  oeeData!: OeeData;
  oeeparetoData!: OeeParetoData;

  machineId!: string;
  name!: string;
  area!: string;

  bosTitle: string = " ";
  paretoTitle: string = " ";
  oeeTitle: string = "";
  oeeParetoTitle: string = "";

  bosChart!: Chart;
  paretoChart!: Chart;
  oeeChart!: Chart;
  oeeParetoChart!: Chart;

  start: Date[] = [];
  end: Date[] = [];

  public rangeOptions: FlatpickrOptions = {
    altInput: true,
    mode: "range",
  };

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    private machineService: MachinesService,
    private chartService: ChartService
  ) {
    this.route.queryParams.subscribe((params) => (this.machineId = params.id));
  }

  setDates($event: Date[], index: number) {
    this.start[index] = $event[0];
    this.end[index] = $event[1];
  }

  get paretoTableData() {
    return this.paretoTable.asObservable();
  }

  get oeeParetoTableData() {
    return this.oeeParetoTable.asObservable();
  }

  ngOnInit(): void {
    this.machineName=this.headerName;
  }

  exportPareto() {
    let format = this.machineName+'_Pareto'+".jpg";
    if (this.paretoChart) {
      let table = document.getElementById("paretoData");
      html2canvas(table).then(function (canvas) {
        var anchorTag = document.createElement("a");
        anchorTag.download = format;
        anchorTag.href = canvas.toDataURL();
        anchorTag.click();
      });
    }
  }

  exportBOS() {
    if (this.bosChart) {
      let a = document.createElement("a");
      a.href = this.bosChart.toBase64Image();
      a.download = "bos-chart.png";
      a.click();
    }
  }

  exportOEE() {
    if (this.oeeChart) {
      let a = document.createElement("a");
      a.href = this.oeeChart.toBase64Image();
      a.download = "oee-chart.png";
      a.click();
    }
  }

  exportOEEPareto() {
    let format = this.machineName+'_OEE_Pareto'+".jpg";
    if (this.oeeParetoChart) {
        let table = document.getElementById("oeeParetoData");
        html2canvas(table).then(function (canvas) {
          var anchorTag = document.createElement("a");
          anchorTag.download = format;
          anchorTag.href = canvas.toDataURL();
          anchorTag.click();
        });
    }
  }

  buildBOSChart() {
    let canvas = this.bosElement.nativeElement;
    let ctx = canvas.getContext("2d");

    if (this.bosChart) {
      if (this.bosData) {
        if (this.bosChart.data?.datasets != null) {
          this.bosChart.data.datasets[0].data = this.bosData.weekly;
          this.bosChart.data.datasets[1].data = this.bosData.weekly4;
          this.bosChart.data.datasets[2].data = this.bosData.weekly12;
          this.bosChart.data.datasets[3].data = this.bosData.lsa;
          this.bosChart.data.datasets[4].data = this.bosData.stretched;
        }

        if (this.bosChart.data?.labels) {
          // @ts-ignore
          this.bosChart.data?.labels = this.bosData.labels;
        }

        if (this.bosChart.options.title) {
          this.bosChart.options.title.text = this.bosTitle;
        }

        this.bosChart.update();
      }
    } else {
      this.bosChart = new Chart(ctx, {
        type: "line",
        options: {
          responsive: true,
          title: {
            display: true,
            text: this.bosTitle,
            fontSize: 20,
          },
          legend: {
            position: "bottom",
            labels: {
              usePointStyle: false,
              // pointStyleWidth:300
              // boxHeight:20
            },
          },
          elements: {
            line: {
              tension: 0,
            },
            point: {
              radius: 1,
            },
          },
          scales: {
            xAxes: [
              {
                gridLines: {
                  color: "rgba(245, 247, 251, 0.7)",
                },
              },
            ],
            yAxes: [
              {
                ticks: {
                  beginAtZero: true,
                },
                gridLines: {
                  color: "rgba(245, 247, 251, 0)",
                },
              },
            ],
          },
        },
        data: {
          datasets: [
            {
              label: "Weekly",
              data: this.bosData.weekly,
              pointStyle: "line",
              backgroundColor: "rgba(191,191,191,1)",
              borderColor: "rgba(191,191,191,1)",
              fill: false,
            },
            {
              label: "4 Weekly",
              data: this.bosData.weekly4,
              pointStyle: "line",
              backgroundColor: "rgba(119,44,42,1)",
              borderColor: "rgba(119,44,42,1)",
              fill: false,
            },
            {
              label: "12 Weekly",
              data: this.bosData.weekly12,
              pointStyle: "line",
              backgroundColor: "rgba(37,73,125,1)",
              borderColor: "rgba(37,73,125,1)",
              fill: false,
            },
            {
              label: "LSA Target",
              data: this.bosData.lsa,
              pointStyle: "dash",
              backgroundColor: "#00cfd5",
              borderColor: "#00cfd5",
              borderCapStyle: "round",
              borderDash: [10, 10],
              fill: false,
            },
            {
              label: "Stretched Target",
              data: this.bosData.stretched,
              pointStyle: "dash",
              backgroundColor: "rgba(146,208,80,1)",
              borderColor: "rgba(146,208,80,1)",
              borderCapStyle: "round",
              borderDash: [10, 10],
              fill: false,
            },
          ],
          labels: this.bosData.labels,
        },
      });
    }
  }

  buildOeeChart() {
    let canvas = this.oeeElement.nativeElement;
    let ctx = canvas.getContext("2d");

    if (this.oeeChart) {
      if (this.oeeData) {
        if (this.oeeChart.data?.datasets != null) {
          this.oeeChart.data.datasets[0].data = this.oeeData.weekly4;
          this.oeeChart.data.datasets[1].data = this.oeeData.weekly12;
          this.oeeChart.data.datasets[2].data = this.oeeData.oeeTarget;
          this.oeeChart.data.datasets[3].data = this.oeeData.baseValue;
          this.oeeChart.data.datasets[4].data = this.oeeData.lsa;
        }

        if (this.oeeChart.data?.labels) {
          // @ts-ignore
          this.oeeChart.data?.labels = this.oeeData.labels;
        }

        if (this.oeeChart.options.title) {
          this.oeeChart.options.title.text = this.oeeTitle;
        }

        this.oeeChart.update();
      }
    } else {
      this.oeeChart = new Chart(ctx, {
        type: "line",
        options: {
          title: {
            display: true,
            text: this.oeeTitle,
            fontSize: 20,
          },
          legend: {
            position: "bottom",
            labels: {
              usePointStyle: true,
            },
          },
          elements: {
            point: {
              radius: 2,
            },
          },
          scales: {
            xAxes: [
              {
                gridLines: {
                  color: "rgba(245, 247, 251, 0.7)",
                },
              },
            ],
            yAxes: [
              {
                type: "linear",
                position: "left",
                id: "y-axis-1",
                stacked: false,
                ticks: {
                  suggestedMin: 0,
                  callback: function (value) {
                    return value + "%";
                  },
                },
                scaleLabel: {
                  display: true,
                  labelString: "Percentage",
                },
              },
              {
                type: "linear",
                position: "right",
                id: "y-axis-2",
                stacked: true,
                ticks: {
                  beginAtZero: true,
                },
                // scaleLabel:{
                //     display:true,
                //     labelString:'Thousands'
                // }
              },
            ],
          },
        },
        data: {
          datasets: [
            {
              label: "OEE (4wk)",
              data: this.oeeData.weekly4,
              pointStyle: "line",
              backgroundColor: "#b5bfc9",
              borderColor: "#b5bfc9",
              fill: false,
              yAxisID: "y-axis-1",
            },
            {
              label: "OEE (12wk)",
              data: this.oeeData.weekly12,
              pointStyle: "line",
              backgroundColor: "#eb813d",
              borderColor: "#eb813d",
              fill: false,
              yAxisID: "y-axis-1",
            },
            {
              label: "Target",
              data: this.oeeData.oeeTarget,
              pointStyle: "dash",
              backgroundColor: "#6288cd",
              borderColor: "#6288cd",
              borderDash: [10, 10],
              fill: false,
              yAxisID: "y-axis-1",
            },
            {
              label: "Baseline",
              data: this.oeeData.baseValue,
              pointStyle: "line",
              backgroundColor: "#9dc3e6",
              borderColor: "#9dc3e6",
              fill: false,
              yAxisID: "y-axis-1",
            },
            {
              label: "LSA (12wk)",
              data: this.oeeData.lsa,
              pointStyle: "dash",
              backgroundColor: "#474747",
              borderColor: "#474747",
              borderCapStyle: "round",
              borderDash: [5, 5],
              fill: false,
              yAxisID: "y-axis-2",
            },
          ],
          labels: this.oeeData.labels,
        },
      });
    }
  }

  buildParetoChart() {
    let canvas = this.paretoElement.nativeElement;
    let ctx = canvas.getContext("2d");

    if (this.paretoChart) {
      if (this.paretoData) {
        if (this.paretoChart.data?.datasets != null) {
          this.paretoChart.data.datasets[0].data = this.paretoData.percentage;
          this.paretoChart.data.datasets[1].data = this.paretoData.downtime;
        }

        if (this.paretoChart.data?.labels) {
          // @ts-ignore
          this.paretoChart.data?.labels = this.paretoData.reasons;
        }

        if (this.paretoChart.options.title) {
          this.paretoChart.options.title.text = this.paretoTitle;
        }

        this.paretoChart.update();
      }
    } else {
      this.paretoChart = new Chart(ctx, {
        type: "line",
        options: {
          legend: {
            position: "bottom",
          },
          title: {
            display: true,
            text: this.paretoTitle,
            fontSize: 20,
          },
          elements: {
            line: {
              tension: 0,
            },
          },
          scales: {
            xAxes: [
              {
                stacked: true,
                scaleLabel: {
                  display: true,
                  labelString: "Stoppage Type",
                },
              },
            ],

            yAxes: [
              {
                type: "linear",
                position: "left",
                id: "y-axis-1",
                stacked: true,
                ticks: {
                  suggestedMin: 0,
                },
                scaleLabel: {
                  display: true,
                  labelString: "Downtime",
                },
              },
              {
                type: "linear",
                position: "right",
                id: "y-axis-2",
                ticks: {
                  suggestedMin: 0,
                  callback: function (value) {
                    return value + "%";
                  },
                },
                scaleLabel: {
                  display: true,
                  labelString: "Percentage",
                },
              },
            ],
          },
        },
        data: {
          labels: this.paretoData.reasons,
          datasets: [
            {
              type: "line",
              label: "Percentage",
              borderColor: "rgba(0, 84, 166,1)",
              backgroundColor: "rgba(0, 84, 166,1)",
              pointBorderWidth: 5,
              fill: false,
              data: this.paretoData.percentage,
              yAxisID: "y-axis-2",
            },
            {
              type: "bar",
              label: "Downtime (in Mins)",
              borderColor: "rgba(125, 167, 217,1)",
              backgroundColor: "rgba(125, 167, 217,1)",
              data: this.paretoData.downtime,
              yAxisID: "y-axis-1",
            },
          ],
        },
      });
    }
  }

  buildOeeParetoChart() {
    let canvas = this.oeeParetoElement.nativeElement;
    let ctx = canvas.getContext("2d");

    if (this.oeeParetoChart) {
      if (this.oeeparetoData) {
        if (this.oeeParetoChart.data?.datasets != null) {
          this.oeeParetoChart.data.datasets[0].data =
            this.oeeparetoData.percentage;
          this.oeeParetoChart.data.datasets[1].data =
            this.oeeparetoData.downtime;
        }

        if (this.oeeParetoChart.data?.labels) {
          // @ts-ignore
          this.oeeParetoChart.data?.labels = this.oeeparetoData.category;
        }

        if (this.oeeParetoChart.options.title) {
          this.oeeParetoChart.options.title.text = this.oeeParetoTitle;
        }

        this.oeeParetoChart.update();
      }
    } else {
      this.oeeParetoChart = new Chart(ctx, {
        type: "line",
        options: {
          legend: {
            position: "bottom",
          },
          title: {
            display: true,
            text: this.oeeParetoTitle,
            fontSize: 20,
          },
          elements: {
            line: {
              tension: 0,
            },
          },
          scales: {
            xAxes: [
              {
                stacked: true,
                scaleLabel: {
                  display: true,
                  labelString: "Stoppage Type",
                },
              },
            ],

            yAxes: [
              {
                type: "linear",
                position: "left",
                id: "y-axis-1",
                stacked: true,
                ticks: {
                  suggestedMin: 0,
                },
                scaleLabel: {
                  display: true,
                  labelString: "Downtime",
                },
              },
              {
                type: "linear",
                position: "right",
                id: "y-axis-2",
                ticks: {
                  suggestedMin: 0,
                  callback: function (value) {
                    return value + "%";
                  },
                },
                scaleLabel: {
                  display: true,
                  labelString: "Percentage",
                },
              },
            ],
          },
        },
        data: {
          labels: this.oeeparetoData.category,
          datasets: [
            {
              type: "line",
              label: "Percentage",
              borderColor: "#1C82AD",
              backgroundColor: "#1C82AD",
              pointBorderWidth: 5,
              fill: false,
              data: this.oeeparetoData.percentage,
              yAxisID: "y-axis-2",
            },
            {
              type: "bar",
              label: "Downtime (in Mins)",
              borderColor: "#FD8A8A",
              backgroundColor: "#FD8A8A",
              data: this.oeeparetoData.downtime,
              yAxisID: "y-axis-1",
            },
          ],
        },
      });
    }
  }

  ngAfterViewInit() {
    // this.buildBOSChart();
    // this.buildParetoChart();
  }

  fetchBOSData() {
    let start = this.datePipe.transform(this.start[0], "dd-MM-yyyy");
    let end = this.datePipe.transform(this.end[0], "dd-MM-yyyy");
    if (start && end) {
      this.chartService
        .bosChart(start, end, this.machineId)
        .subscribe((data) => {
          this.bosData = data;
          this.bosTitle =
            "BOS Chart: " +
            " (" +
            (start || "01 Jan 10") +
            " To " +
            (end || "01 Jan 10") +
            ")";
          this.buildBOSChart();
        });
    }
  }

  fetchParetoData() {
    let starting = this.datePipe.transform(this.start[1], "dd-MM-yyyy");
    let ending = this.datePipe.transform(this.end[1], "dd-MM-yyyy");
    if (starting && ending) {
      this.chartService
        .paretoChart(starting, ending, this.machineId)
        .subscribe((data) => {
          this.paretoData = data;
          this.paretoTitle =
            "Pareto Chart: " +
            " (" +
            (starting || "01 Jan 10") +
            " To " +
            (ending || "01 Jan 10") +
            ")";
          this.buildParetoChart();
          this.paretoTable.next(data);
        });
    }
  }

  fetchOeeData() {
    let starting = this.datePipe.transform(this.start[2], "dd-MM-yyyy");
    let ending = this.datePipe.transform(this.end[2], "dd-MM-yyyy");
    if (starting && ending) {
      this.chartService
        .oeeChart(starting, ending, this.machineId)
        .subscribe((data) => {
          this.oeeData = data;
          this.oeeTitle =
            "OEE BOS Chart: " +
            " (" +
            (starting || "01 Jan 10") +
            " To " +
            (ending || "01 Jan 10") +
            ")";
          this.buildOeeChart();
        });
    }
  }

  fetchOeeParetoData() {
    let starting = this.datePipe.transform(this.start[3], "dd-MM-yyyy");
    let ending = this.datePipe.transform(this.end[3], "dd-MM-yyyy");
    if (starting && ending) {
      this.chartService
        .oeeParetoChart(starting, ending, this.machineId)
        .subscribe((data) => {
          this.oeeparetoData = data;
          this.oeeParetoTitle =
            "OEE Pareto Chart: " +
            " (" +
            (starting || "01 Jan 10") +
            " To " +
            (ending || "01 Jan 10") +
            ")";
          this.buildOeeParetoChart();
          this.oeeParetoTable.next(data);
        });
    }
  }
}
