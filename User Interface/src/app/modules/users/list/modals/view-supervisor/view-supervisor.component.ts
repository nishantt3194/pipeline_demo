import { Component, Input, OnInit, TemplateRef } from "@angular/core";
import { Router } from "@angular/router";
import {
  NgbActiveModal,
  NgbModal,
  NgbModalOptions,
} from "@ng-bootstrap/ng-bootstrap";
import { UsersInfo, UsersRef } from "app/modules/users/dao/users.models";
import { UsersService } from "app/modules/users/services/users.service";
import { id } from "chartjs-plugin-annotation";

@Component({
  selector: "app-view-supervisor",
  templateUrl: "./view-supervisor.component.html",
  styleUrls: ["./view-supervisor.component.scss"],
})
export class ViewSupervisorComponent implements OnInit {
  @Input() ref: UsersRef;
  machines!: string[];
  operatorMachines!: string[];
  showAssign: boolean = false;
  roleUpdate: string = "";
  showUnmap: boolean = false;
  selectedMachines: { display: string; value: string }[] = [];
  machine: string = "";
  constructor(
    public modal: NgbActiveModal,
    public modalService: NgbModal,
    private router: Router,

    private _usersService: UsersService
  ) {}

  ngOnInit(): void {}
  initRoleUpdate(
    content: TemplateRef<any>,
    modalOptions: NgbModalOptions = {},
    email: string,
    role: string
  ) {
    this.modalService.open(content, modalOptions).result.then((result) => {
      if (result != "Close") {
        this.modal.close("Close");
        this._usersService.updateUserRole(role, status).subscribe(
          (ref) => {
            let currentUrl = this.router.url;
            this.router.routeReuseStrategy.shouldReuseRoute = () => false;
            this.router.onSameUrlNavigation = "reload";
            this.router.navigate([currentUrl]);
          },
          (error) => {
            let currentUrl = this.router.url;
            this.router.routeReuseStrategy.shouldReuseRoute = () => false;
            this.router.onSameUrlNavigation = "reload";
            this.router.navigate([currentUrl]);
          }
        );
      }
    });
  }
}
