import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { HeaderItem } from "app/modules/app-common/models/app-common.models";
import { Subject } from "rxjs";
import { DashboardService } from "../../services/dashboard.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import {
  NewEntriesHeaders,
  StagedEntriesHeaders,
} from "../../dao/dashboard.headers";
import { DashboardInfo } from "../../dao/dashboard.models";
import { AuthenticationService } from "app/auth/service";
import { LoggedInUser } from "app/auth/models";
@Component({
  selector: "app-container",
  templateUrl: "./container.component.html",
  styleUrls: ["./container.component.scss"],
})
export class ContainerComponent implements OnInit {

    user: LoggedInUser;
    type: 'ADMIN' | 'SUPERVISOR' | 'OPERATOR';
  
    constructor(private _authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this._authenticationService.currentUser
        .subscribe({
      next: user => {
        this.user = user;
        this.type = LoggedInUser.isAdmin(this.user) ? 'ADMIN' : LoggedInUser.isSupervisor(this.user) ? 'SUPERVISOR' : 'OPERATOR';
      }
    })
    
    
  }
}
