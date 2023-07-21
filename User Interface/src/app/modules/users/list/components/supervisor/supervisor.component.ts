import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { NgbModal, NgbModalOptions } from "@ng-bootstrap/ng-bootstrap";
import {
  HeaderItem,
  Page,
} from "app/modules/app-common/models/app-common.models";
import { SupervisorHeader } from "app/modules/users/dao/users.headers";
import { UsersRef } from "app/modules/users/dao/users.models";
import { ListService } from "app/modules/users/services/list.service";
import { Observable, Subject } from "rxjs";
import { AddUserComponent } from "../../modals/add-user/add-user.component";
import { ViewSupervisorComponent } from "../../modals/view-supervisor/view-supervisor.component";

@Component({
  selector: "app-supervisor",
  templateUrl: "./supervisor.component.html",
  styleUrls: ["./supervisor.component.scss"],
})
export class SupervisorComponent implements OnInit {
  headers: HeaderItem[];
  builder: (
    page: number,
    size: number,
    args?: any
  ) => Observable<Page<UsersRef>>;
  private _unsubscribeAll: any;

  constructor(
    private _listService: ListService,
    private _router: Router,
    public modalService: NgbModal
  ) {
    this.openNewUserDialog = this.openNewUserDialog.bind(this);

    this._unsubscribeAll = new Subject();
    this.headers = SupervisorHeader;
    this.builder = _listService.fetchUserList;
  }

  ngOnInit(): void {}

  viewSupervisor(user: any): void {
    console.log(user);
    this._router.navigate(["/users/view",], { queryParams: { id: user.id } });

   
  }

  ngOnDestroy(): void {
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
}j
  openNewUserDialog() {
    const modalOptions: NgbModalOptions = {
      centered: true,
    };

    const modalRef = this.modalService.open(AddUserComponent, modalOptions);
  }
}
