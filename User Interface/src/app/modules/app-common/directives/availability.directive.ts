import {Directive, Input} from '@angular/core';
import {AbstractControl, AsyncValidator, NG_ASYNC_VALIDATORS, ValidationErrors} from "@angular/forms";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Directive({
    selector: '[availability]',
    providers: [{provide: NG_ASYNC_VALIDATORS, useExisting: AvailabilityDirective, multi: true}]
})
export class AvailabilityDirective implements AsyncValidator {

    @Input('availability') availabilityControl: (val: string) => Observable<boolean>;

    validate(control: AbstractControl): Observable<ValidationErrors | null> {
        return this.availabilityControl(control.value)
            .pipe(map(isAvailable => isAvailable ? null : {availability: false}));
    }


}
