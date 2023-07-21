import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CategoryInfo, MachineSearch } from '../../dao/settings.models';
import { CategoryService } from '../../services/category.service';
import { DowntimeService } from '../../services/downtime.service';
import { MachinesService } from 'app/modules/machines/services/machines.service';
import { StationService } from 'app/modules/machines/services/station.service';
import { StationSearch } from 'app/modules/machines/dao/machines.models';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-map-reason',
  templateUrl: './map-reason.component.html',
  styleUrls: ['./map-reason.component.scss']
})
export class MapReasonComponent implements OnInit {
  @Input() parentCategory: string;
  @Input() subCategory: string;
  @Input() reason: string;
  categories: CategoryInfo[];
  @Input() reasons: any[];
  machines: MachineSearch[] = [];
  stations: StationSearch[] = [];
  machine: string;
  station: string;

  constructor(
    private _categoryService: CategoryService,
    private _modal: NgbActiveModal,
    private _downtimeService: DowntimeService,
    private _machineService: MachinesService,
    private _stationService: StationService
  ) {}

  ngOnInit(): void {
    this._categoryService.categories.subscribe((categories) => {
      this.categories = categories;
    });

    this._machineService.search().subscribe({
      next: machines => {
        this.machines = machines;
      }
    })
  }

  getSubCategories(id: string): CategoryInfo[] {
    let category = this.categories.find(
      (category) => category.identifier === id
    );
    return category ? category.subcategory : [];
  }

  refreshStations(machine: string) {
    this._stationService.search(machine).subscribe({
      next: stations => {
        this.stations = stations;
      }
    });
  }

  close() {
    this._modal.dismiss();
  }

  mapReason(form: NgForm) {
    if(form.valid) {
      this._downtimeService.map({
        machine: this.machine,
        station: this.station,
        reason: this.reason,
      }).subscribe({
        complete: () => {
          this._modal.close();
        }
      })
    }
  }

}
