import { Component, OnInit } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { DefaultRoles } from "app/modules/users/dao/users.data";
import { CreateUserPayload } from "app/modules/users/dao/users.models";
import { CreateService } from "app/modules/users/services/create.service";

@Component({
  selector: "app-add-user",
  templateUrl: "./add-user.component.html",
  styleUrls: ["./add-user.component.scss"],
})
export class AddUserComponent implements OnInit {
  userRef = new CreateUserPayload();
  roles: { identifier: string; label: string }[];

  saving: boolean = false;

  constructor(
    public modal: NgbActiveModal,
    private _createService: CreateService
  ) {
    this.roles = DefaultRoles;
  }

  ngOnInit(): void {}
  save() {
    this.saving = true;

    this._createService.create(this.userRef).subscribe({
      complete: () => {
        this.saving = false;
        this.modal.close({ action: "saved" });
      },
    });
  }
}
