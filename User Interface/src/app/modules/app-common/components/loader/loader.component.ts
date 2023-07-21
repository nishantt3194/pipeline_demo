import {Component, OnInit} from '@angular/core';
import {NavigationEnd, NavigationStart, Router} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {filter} from "rxjs/operators";

@Component({
    selector: 'app-loader',
    templateUrl: './loader.component.html',
    styleUrls: ['./loader.component.scss']
})
export class LoaderComponent implements OnInit {
    constructor(private _router: Router) {
    }

    _show: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);

    get show(): Observable<boolean> {
        return this._show.asObservable();
    }

    ngOnInit(): void {
        this._router.events
            .pipe(filter(event => event instanceof NavigationStart))
            .subscribe({
                next: (event) => {
                    this._show.next(true);
                }
            });

        this._router.events
            .pipe(filter(event => event instanceof NavigationEnd))
            .subscribe({
                next: (event) => {
                    this._show.next(false);
                }
            });
    }

}
