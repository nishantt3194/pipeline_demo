import { Component, Input, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { CreateService } from "app/modules/entries/services/create.service";
import { MachineMinimalRef, MachineSearch } from "app/modules/machines/dao/machines.models";
import { MachinesService } from "app/modules/machines/services/machines.service";
import { AssignMachine, UserSearch, userIds } from "app/modules/users/dao/users.models";
import { UsersService } from "app/modules/users/services/users.service";
import { ViewService } from "app/modules/users/services/view.service";

@Component({
  selector: "app-unmap-machine",
  templateUrl: "./unmap-machine.component.html",
  styleUrls: ["./unmap-machine.component.scss"],
})
export class UnmapMachineComponent implements OnInit {
  ref = new AssignMachine();

  @Input() userRef: string[] = [];
  @Input() machineRef: string[] = [];
  machines: { label: string; identifier: string }[];
  saving: boolean = false;
  users: { label: string; identifier: string }[];

  selectedMachines:  { display: string; value: string }[] = [];
 operator:string;

  constructor(
    private _machineService: MachinesService,
    private _usersService: UsersService,
    private _createService: CreateService,
    private _viewService : ViewService,
    public modalService: NgbModal,
    public modal: NgbActiveModal,
    public _route: ActivatedRoute
  ) {
    this.ref = new AssignMachine();
    this._route.queryParams.subscribe((user) => (this.operator = user.id));
  }

  ngOnInit(): void {
    this._machineService.searchMachine(this.operator).subscribe((machines: MachineSearch[]) => {

      this.machines = machines;
    });
   
    
    this._usersService.search().subscribe((users: UserSearch[]) => {
      this.users = users;
    });
 
  }
  save() {
    
    // this.ref.userIds = this.userIds.map((userId) => userId.identifier);
    this.saving = true;
    this._usersService
      .assign({
        userIds: this.userRef,
        machineIds: this.machineRef,
        assign: "UNASSIGN", 
      })
      .subscribe({
        complete: () => {
          this.saving = false;
          this.modal.close({ action: "saved" });
        },
      });
      window.location.reload();
  }

  //   delete(i: number) {
  //     this.userId.splice(i, 1);
  // }
}
