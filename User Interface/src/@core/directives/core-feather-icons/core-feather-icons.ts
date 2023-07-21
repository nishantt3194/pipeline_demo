/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {ChangeDetectorRef, Directive, ElementRef, Inject, Input, OnChanges, SimpleChanges} from '@angular/core';

import * as Feather from 'feather-icons';
import {HttpClient} from "@angular/common/http";

@Directive({
    selector: '[data-feather]'
})
export class FeatherIconDirective implements OnChanges {
    @Input('data-feather') name!: string;
    @Input() class!: string;
    @Input() size!: string;
    @Input() inner!: boolean;
    // Private
    private _nativeElement: any;

    /**
     * Constructor
     *
     * @param {ElementRef} _elementRef
     */
    constructor(
        @Inject(ElementRef) private _elementRef: ElementRef,
        @Inject(ChangeDetectorRef) private _changeDetector: ChangeDetectorRef,
    ) {
    }

    ngOnChanges(changes: SimpleChanges) {
        // Get the native element
        this._nativeElement = this._elementRef.nativeElement;

        // SVG parameter
        this.name = changes.name ? changes.name.currentValue : '';
        this.size = changes.size ? changes.size.currentValue : '14'; // Set default size 14
        this.class = changes.class ? changes.class.currentValue : '';

        // Create SVG
        const svg = Feather.icons[this.name].toSvg({
            class: this.class,
            width: this.size,
            height: this.size
        });

        // Set SVG
        if (this.inner) {
            this._nativeElement.innerHTML = svg;
        } else {
            this._nativeElement.outerHTML = svg;
        }
        this._changeDetector.markForCheck();
    }
}
