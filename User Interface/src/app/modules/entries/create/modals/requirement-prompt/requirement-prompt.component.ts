import {Component, Input, OnInit} from '@angular/core';
import {CategoryRequirement} from "../../../../settings/dao/settings.models";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-requirement-prompt',
  templateUrl: './requirement-prompt.component.html',
  styleUrls: ['./requirement-prompt.component.scss']
})
export class RequirementPromptComponent implements OnInit {
  @Input() public requirements: CategoryRequirement[];
  constructor(private _modal: NgbActiveModal) { }

  ngOnInit(): void {
  }

  saveRequirement(req: CategoryRequirement) {
    this._modal.close({action: 'save', id: req != null ? req.derivedCategory : null});
  }
}
