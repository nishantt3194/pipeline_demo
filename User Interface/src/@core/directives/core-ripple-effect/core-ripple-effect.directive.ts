/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Directive, ElementRef, Input} from '@angular/core';

import * as Waves from 'node-waves';

@Directive({
    selector: '[rippleEffect]'
})
export class RippleEffectDirective {
    @Input() wave: string;
    // Private
    private _nativeElement: any;

    /**
     * Constructor
     *
     * @param {ElementRef} _elementRef
     */
    constructor(private _elementRef: ElementRef) {
    }

    ngOnInit(): void {
        // Get the native element
        this._nativeElement = this._elementRef.nativeElement;

        if (
            // Attach ripple with light style i.e solid variant of button
            !this._nativeElement.className.split(' ').some(function (c) {
                return /btn-outline-.*/.test(c);
            }) &&
            !this._nativeElement.className.split(' ').some(function (c) {
                return /btn-flat-.*/.test(c);
            })
        ) {
            Waves.attach(this._nativeElement, ['waves-float', 'waves-light']);
        } else {
            // Attach ripple with transparent style i.e flat, outline variant of button
            Waves.attach(this._nativeElement);
        }
    }
}
