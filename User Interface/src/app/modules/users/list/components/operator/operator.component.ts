import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { NgbModal, NgbModalOptions } from "@ng-bootstrap/ng-bootstrap";
import {
  HeaderItem,
  Page,
} from "app/modules/app-common/models/app-common.models";
import { UsersRef } from "app/modules/users/dao/users.models";
import { ListService } from "app/modules/users/services/list.service";
import { Observable, Subject } from "rxjs";
import { AddUserComponent } from "../../modals/add-user/add-user.component";
import { ViewOperatorComponent } from "../../modals/view-operator/view-operator.component";
import { OperationLogHeader } from "app/modules/logs/dao/logs.headers";
import { OperatorHeaders } from "app/modules/users/dao/users.headers";
import { AssignMachineComponent } from "../../../view/modals/assign-machine/assign-machine.component";

@Component({
  selector: "app-operator",
  templateUrl: "./operator.component.html",
  styleUrls: ["./operator.component.scss"],
})
export class OperatorComponent implements OnInit {
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
    this.assignMachine = this.assignMachine.bind(this);
    this.viewOperator = this.viewOperator.bind(this);

    this._unsubscribeAll = new Subject();
    this.headers = OperatorHeaders;
    this.builder = _listService.fetchUserList;
  }

  ngOnInit(): void {}

  viewOperator(user: any): void {
    this._router.navigate(["/users/view"], { queryParams: { id: user.id } });

    // const modalOptions: NgbModalOptions = {
    //     centered: true,
    //     size: "lg",
    // };
    // let ref = this.modalService.open(
    //     ViewOperatorComponent,
    //     modalOptions
    // );
    // ref.componentInstance.ref = row;
  }
  openNewUserDialog() {
    const modalOptions: NgbModalOptions = {
      centered: true,
    };

    const modalRef = this.modalService.open(AddUserComponent, modalOptions);
  }
  ngOnDestroy(): void {
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
}

  assignMachine(rows: any[]) {
    let userId = rows.map((row) => {
      return row.id;
    });

    if (userId.length === 0) {
      return;
    }

    let options: NgbModalOptions = {
      centered: true,
      size: "lg",
    };

    let ref = this.modalService.open(AssignMachineComponent, options);
    ref.componentInstance.userRef = userId;
  }
}
