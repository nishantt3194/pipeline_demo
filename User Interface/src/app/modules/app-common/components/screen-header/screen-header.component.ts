import {Component, Inject, Input, OnInit} from '@angular/core';
import {DOCUMENT} from "@angular/common";

@Component({
    selector: 'app-screen-header',
    templateUrl: './screen-header.component.html',
    styleUrls: ['./screen-header.component.scss']
})
export class ScreenHeaderComponent implements OnInit {
    @Input() icon: string;
    @Input() title: string;
    @Input() subtitle?: string;

    constructor(@Inject(DOCUMENT) private document: Document) {}


    ngOnInit(): void {
        // window.addEventListener('scroll', this.onWindowScroll, true);

    }

    onWindowScroll(event: any) {
        if (this.document.body.scrollTop > 50 || this.document.documentElement.scrollTop > 50) {
            document.getElementsByClassName('header-outer')[0].classList.remove("expanded");
        }
        else {
            document.getElementsByClassName('header-outer')[0].classList.add("expanded");
        }
    }

}
