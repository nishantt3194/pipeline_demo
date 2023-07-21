import {Component, OnInit} from '@angular/core';
import {SubmitBlockerService} from '../../services/submit-blocker.service';

@Component({
    selector: 'app-submit-blocker',
    templateUrl: './submit-blocker.component.html',
    styleUrls: ['./submit-blocker.component.scss']
})
export class SubmitBlockerComponent implements OnInit {
    show: boolean = false;

    constructor(private _submitBlockService: SubmitBlockerService) {
    }

    ngOnInit(): void {
        this._submitBlockService.isSubmittingData.subscribe({
            next: (value) => {
                this.show = value;
            }
        })
    }

}
