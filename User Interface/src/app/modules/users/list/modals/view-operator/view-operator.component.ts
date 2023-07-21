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
  selector: "app-view-operator",
  templateUrl: "./view-operator.component.html",
  styleUrls: ["./view-operator.component.scss"],
})
export class ViewOperatorComponent implements OnInit {
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
    private _usersService: UsersService,
    public modalService: NgbModal,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ref.machines = this.ref.machines.sort(
      (m1, m2) => m1.length - m2.length
    );
    this._usersService
      .getOperatorMachineNames(this.ref.email)
      .subscribe((ref) => {
        this.operatorMachines = ref;
      });

    this._usersService.getAllMachineNames().subscribe((ref) => {
      this.machines = ref
        .sort((a, b) => b.length - a.length)
        .filter((m) => this.ref.machines.indexOf(m) === -1);
    });
  }
  unmapOperator() {
    this._usersService.unmapOperator(this.ref.email, this.machine).subscribe(
      (ref) => {
        this.modal.close("Cancel");

        let currentUrl = this.router.url;
        ref;
        this.router.routeReuseStrategy.shouldReuseRoute = () => false;
        this.router.onSameUrlNavigation = "reload";
        this.router.navigate([currentUrl]);
      },
      (error) => {
        this.modal.close("Cancel");

        let currentUrl = this.router.url;
        this.router.routeReuseStrategy.shouldReuseRoute = () => false;
        this.router.onSameUrlNavigation = "reload";
        this.router.navigate([currentUrl]);
      }
    );
  }

  assignUser() {
    this.showAssign = true;
  }
  initRoleUpdate(
    content: TemplateRef<any>,
    modalOptions: NgbModalOptions = {},
    email: string,
    role: string
  ) {
    this.modalService.open(content, modalOptions).result.then((result) => {
      if (result != "Close") {
        this.modal.close("Close");
        this._usersService.updateUserRole( role, status).subscribe(
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
  unMap() {
    // if (this.ref.assigned) {
      this.showUnmap = true;
    // }
  }
  closeAssign() {
    this.showAssign = false;
}

closeUnmap() {
    this.showUnmap = false;
}
}
