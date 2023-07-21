import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EditMachineRef, EditPrecheckRef, MachineInfo, PrecheckInfo, ReasonSearch } from 'app/modules/machines/dao/machines.models';
import { ViewService } from 'app/modules/machines/services/view.service';

@Component({
  selector: 'app-edit-precheck',
  templateUrl: './edit-precheck.component.html',
  styleUrls: ['./edit-precheck.component.scss']
})
export class EditPrecheckComponent implements OnInit {
  ref:PrecheckInfo;
  payload:EditPrecheckRef=new EditPrecheckRef();

  @Input('flag') flag:string;
  // @Input('precheck') precheck:EditPrecheckRef;

  submitting: boolean = false;
  machineId: string;
  reasonSearch: ReasonSearch[];
  isUpdate:boolean;
  
  constructor(public modalService: NgbModal,
    public modal: NgbActiveModal,
    private _viewService: ViewService,
    private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => this.machineId = params.id);
      
  }

  ngOnInit(): void {
    this.isUpdate=this.flag==="update"?true:false;
    this._viewService.fetchCommonReason.subscribe({
      next: (reasons) => {
        this.reasonSearch = reasons;
      }
    })
    
  }

  save(editForm: NgForm) {
    if (editForm.form.valid) {
      this.submitting = true;
      this.ref.machine = this.machineId;
      this._viewService.addPrecheck(this.ref).subscribe((response) => {
        this.submitting = false;
        this.modal.close('save');
        window.location.reload();
      });
    }
  }

  update(editForm: NgForm){
    if (editForm.form.valid) {
      this.submitting = true;
      // this.payload.id=this.
      this.payload.reason=this.ref.reason;
      this.payload.time=this.ref.time;
      this.payload.id=this.ref.id;

      this._viewService.updatePrecheck(this.payload).subscribe((response) => {
        this.submitting = false;
        this.modal.close('save');
        window.location.reload();
      });
    }
  }

}
