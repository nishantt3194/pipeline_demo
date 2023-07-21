import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {NewTemplatePayload, Template} from 'app/modules/products/dao/products.models';

@Component({
    selector: 'app-new-template',
    templateUrl: './new-template.component.html',
    styleUrls: ['./new-template.component.scss']
})
export class NewTemplateComponent implements OnInit {

    @Input() area!: string;
    @Input() templateType!: string;
    public newTemplate!: FormGroup;
    @Input() templates!: Template[];

    conversion: boolean = false;
    constant: boolean = false;
    template: boolean = false;

    constructor(public modal: NgbActiveModal,
                private _fb: FormBuilder) {
    }

    get formArr() {
        return this.newTemplate.get('templates') as FormArray;
    }

    addFields() {
        this.addRows();
    }

    addRows() {
        this.formArr.push(this.initRows());
    }


    ngOnInit() {
        if (this.area && this.templateType) {
            this.newTemplate = this._fb.group({
                templates: this._fb.array([]),
            });

            if (this.templates.length > 0) {
                for (let i = 0; i < this.templates.length; i++) {
                    this.formArr.push(this.buildRows(this.templates[i]));
                }
            } else {
                this.addRows();
            }

        } else {
            this.modal.close('error');
        }
    }

    buildRows(template: Template) {
        let i: number = this.formArr.length;
        return this._fb.group({
            id: [template.id],
            sign: [template.sign],
            description: [template.description],
            toConvert: [template.toConvert],
            operation: [template.operation],
            operand: [template.operand],
            formulaType: [template.type],
            templateType: [this.templateType],
            area: [this.area]
        });
    }

    initRows() {
        let i: number = this.formArr.length;
        return this._fb.group({
            id: [null],
            sign: ['ADD'],
            description: [''],
            toConvert: [false],
            operation: ['DIVIDE'],
            operand: [null],
            formulaType: [''],
            templateType: [this.templateType],
            area: [this.area]
        });
    }

    addConversion(i: number) {
        let c: FormGroup = this.formArr['controls'][i] as FormGroup;
        c.controls['toConvert'].setValue(true);
    }

    typeSelected($event: any) {
        let val: string = $event.target.value;
        this.constant = val !== 'WEIGHT';
    }

    save() {

        let payload = this.newTemplate.value.templates as NewTemplatePayload[];
        this.modal.close({action: 'save', payload: payload});
        this.modal.close('Close');
    }

}
