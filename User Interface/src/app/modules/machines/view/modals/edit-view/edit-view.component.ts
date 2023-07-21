import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EditMachineRef, MachineInfo } from 'app/modules/machines/dao/machines.models';
import { ViewService } from 'app/modules/machines/services/view.service';

@Component({
  selector: 'app-edit-view',
  templateUrl: './edit-view.component.html',
  styleUrls: ['./edit-view.component.scss']
})
export class EditViewComponent implements OnInit {
  @Input() ref: EditMachineRef;
  machineInfo: MachineInfo;
  submitting: boolean = false;

  constructor(public modalService: NgbModal,
    public modal: NgbActiveModal,
    private _viewService : ViewService) { }

  ngOnInit(): void {
  }

  save(editForm: NgForm) {
    if(editForm.form.valid) {
        this.submitting = true;
        this._viewService.editMachine(this.ref).subscribe({
            next: info => {
                this._viewService.machineInfo = info.data;
                this._viewService.onMachineChange.next(info.data);
            },
            complete: () => {
                this.submitting = false;
                this.modal.close('save');
            }
        });
        window.location.reload();
    }
}

}
