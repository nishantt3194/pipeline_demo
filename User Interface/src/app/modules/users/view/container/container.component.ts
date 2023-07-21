import {
  Component,
  Input,
  OnInit,
  TemplateRef,
  ViewEncapsulation,
} from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { MachineHeaders } from "../../../machines/dao/machines.headers";
import { UsersInfo, UsersRef } from "../../dao/users.models";
import { ViewService } from "../../services/view.service";
import {
  NgbActiveModal,
  NgbModal,
  NgbModalOptions,
} from "@ng-bootstrap/ng-bootstrap";
import { AssignMachineComponent } from "../modals/assign-machine/assign-machine.component";
import { EntryInfo } from "app/modules/entries/dao/shifts.models";
import { EntriesService } from "app/modules/entries/services/entries.service";
import { Observable, Subject } from "rxjs";
import {
  HeaderItem,
  Page,
} from "app/modules/app-common/models/app-common.models";
import { MachineMinimalRef } from "app/modules/machines/dao/machines.models";
import { UnmapMachineComponent } from "../modals/unmap-machine/unmap-machine.component";

@Component({
  selector: "app-container",
  templateUrl: "./container.component.html",
  styleUrls: ["./container.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class ContainerComponent implements OnInit {
  entries: EntryInfo[];
  ref: UsersInfo;
  private _unsubscribeAll: any;

  email: string;
  machine: string;
  selectedMachines: { display: string; value: string }[] = [];

  status = true;
  builder: (
    offset: number,
    size: number,
    args?: any
  ) => Observable<Page<MachineMinimalRef>>;
  constructor(
    private _route: ActivatedRoute,
    // public modal: NgbActiveModal,
    private _router: Router,
    private _viewService: ViewService,
    public modalService: NgbModal,
    private _entriesService: ViewService
  ) {
    this.builder = this._viewService.getOperatorMachine;

    this._unsubscribeAll = new Subject();
  }

  ngOnInit(): void {
    this._viewService.onUserChange.subscribe({
      next: (user) => {
        this.ref = user;
      },
    });

    console.log("HII");
  }

  openMachine(id: string) {
    this._router.navigate(["/machines/view"], { queryParams: { id: id } });
  }

  get headers() {
    return MachineHeaders;
  }

  initRoleUpdate(
    content: TemplateRef<any>,
    modalOptions: NgbModalOptions = {},
    id: string,
    status: boolean,
    role: string
  ) {
    this.modalService.open(content, modalOptions).result.then((result) => {
      if (result != "Close") {
        this._viewService.updateUserRole(id, status, role).subscribe(
          (ref) => {
            let currentUrl = this._router.url;
            this._router.routeReuseStrategy.shouldReuseRoute = () => false;
            this._router.onSameUrlNavigation = "reload";
            this._router.navigate([currentUrl]);
          },
          (error) => {
            let currentUrl = this._router.url;
            this._router.routeReuseStrategy.shouldReuseRoute = () => false;
            this._router.onSameUrlNavigation = "reload";
            this._router.navigate([currentUrl]);
          }
        );
      }
    });
  }

  statusUpdate(
    content: TemplateRef<any>,
    modalOptions: NgbModalOptions = {},
    id: string,
    status: boolean,
    role: string
  ) { this.modalService.open(content, modalOptions).result.then((result) => {
    if (result != "Close") {
      this._viewService.updateUserRole(id, status, role).subscribe(
        (ref) => {
          let currentUrl = this._router.url;
          this._router.routeReuseStrategy.shouldReuseRoute = () => false;
          this._router.onSameUrlNavigation = "reload";
          this._router.navigate([currentUrl]);
          window.location.reload();
        },
        (error) => {
          let currentUrl = this._router.url;
          this._router.routeReuseStrategy.shouldReuseRoute = () => false;
          this._router.onSameUrlNavigation = "reload";
          this._router.navigate([currentUrl]);
        }
      );
    }
  });}

  enable() {}
  disable() {}

  assignMachine() {
    const modalOptions: NgbModalOptions = {
      centered: true,
      size: "lg",
    };
    let ref = this.modalService.open(AssignMachineComponent, modalOptions);
    ref.componentInstance.userRef = [this.ref.id];
  }

  unmapOperator() {
    const modalOptions: NgbModalOptions = {
      centered: true,
      size: "lg",
    };
    let ref = this.modalService.open(UnmapMachineComponent, modalOptions);
    ref.componentInstance.userRef = [this.ref.id];
    // ref.componentInstance.machineRef =[this.ref.id]
  }
}
