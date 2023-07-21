import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SubmitBlockerService {
    public isSubmittingData: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

    constructor() {
    }


    public show() {
        this.isSubmittingData.next(true);
    }

    public hide() {
        this.isSubmittingData.next(false);
    }
}
