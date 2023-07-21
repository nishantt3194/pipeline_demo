import {ChangeDetectorRef, Directive, ElementRef, Inject, Input, OnChanges, SimpleChanges} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";

@Directive({
    selector: '[data-icon]'
})
export class CoreSolarIconsDirective implements OnChanges {
    @Input('data-icon') name!: string;
    @Input() class!: string;
    @Input() size!: string;
    @Input() inner!: boolean;

    private _nativeElement: any;

    constructor(
        @Inject(ElementRef) private _elementRef: ElementRef,
        @Inject(ChangeDetectorRef) private _changeDetector: ChangeDetectorRef,
        private _http: HttpClient,
        private _sanitizer: DomSanitizer,
    ) {

    }

    ngOnChanges(changes: SimpleChanges): void {
        this._nativeElement = this._elementRef.nativeElement;

        // SVG parameter
        this.name = changes.name ? changes.name.currentValue : '';
        this.size = changes.size ? changes.size.currentValue : '16';
        this.class = changes.class ? changes.class.currentValue : '';
        // const svg = Icons[this.name];

        this._http.get(`assets/icons/${this.name}.svg`, {responseType: 'text'}).subscribe((svgRaw) => {
            const svg = svgRaw.replaceAll("___classes___", this.class);

            if (this.inner) {
                this._nativeElement.innerHTML = svg;
            } else {
                this._nativeElement.outerHTML = svg;
            }
            this._changeDetector.markForCheck();
        });
    }

}
