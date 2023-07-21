import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { EntriesService } from "../../services/entries.service";
import { FlatpickrOptions } from "ng2-flatpickr";
import { AreaSearch } from "../../../settings/dao/settings.models";
import { AreaService } from "../../../settings/services/area.service";

@Component({
  selector: "app-container",
  templateUrl: "./container.component.html",
  styleUrls: ["./container.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class ContainerComponent implements OnInit {
  entriesToday: number = -1;
  currentTab: "pending" | "today" | "reports" | "zll_reports" = "today";
  start: Date;
  end: Date;
  areaId: string;

  areas: AreaSearch[];

  public rangeOptions: FlatpickrOptions = {
    altInput: true,
    mode: "range",    
  };

  constructor(
    private _entriesService: EntriesService,
    private _areaService: AreaService
  ) {}

  ngOnInit(): void {
    this._entriesService.entriesToday.subscribe(
      (value) => (this.entriesToday = value)
    );
    this._areaService.search().subscribe((areas: AreaSearch[]) => {
      this.areas = areas;
    });
    if(localStorage.getItem('startDate')==null){
      this.start = new Date(localStorage.getItem('startDate'));
      this.end = new Date(localStorage.getItem('endDate'));
    }
  }

  setDates($event: Date[]) {
    this.start = $event[0];
    this.end = $event[1];
    localStorage.removeItem('fetchedData');
    localStorage.setItem('startDate',this.start.toString());
    localStorage.setItem('endDate',this.end.toString());

    this._entriesService.entryRange.next([this.start, this.end]);
  }
}
