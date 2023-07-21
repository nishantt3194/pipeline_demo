/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {
    Component,
    EventEmitter,
    Input,
    OnChanges,
    OnInit,
    Output,
    SimpleChanges,
    ViewChild,
    ViewEncapsulation
} from '@angular/core';

import {BehaviorSubject, fromEvent, Observable} from 'rxjs';
import {ColumnMode, DatatableComponent, SelectionType} from '@swimlane/ngx-datatable';
import {HttpClient} from '@angular/common/http';
import {Params, Router} from '@angular/router';
import {v4 as uuid} from 'uuid';
import {getTextWidth, HeaderItem, Page, RowCallback} from "../../models/app-common.models";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {jsons2csv} from "@ctrl/ngx-csv";
import {debounceTime, filter, map} from "rxjs/operators";

@Component({
    selector: 'app-table-builder',
    templateUrl: './table-builder.component.html',
    styleUrls: ['./table-builder.component.scss'],
    encapsulation: ViewEncapsulation.None

})
export class TableBuilderComponent implements OnInit, OnChanges {
    @Input() mode: 'builder' | 'data';
    @Input() headers: HeaderItem[];
    @Input() idField: string;
    @Input() builder: (page: number, size: number, args?: any) => Observable<Page<any>>;
    @Input() builderArgs?: any;
    @Input() data: any[];

    @Input() toolbar: boolean = true;
    @Input() export: boolean = true;
    @Input() selectable: boolean = true;
    @Input() search: boolean = true;
    @Input() create: boolean = false;
    @Input() delete: boolean = false;
    @Input() filter: boolean = true;
    @Input() actions: boolean = false;
    @Input() disableAllActions: boolean = false;
    @Input() disableFooter: boolean = false;
    @Input() detailHeight: number = 50;
    @Input() addDetailPane: boolean = true;
    @Input() createCallback: () => void;
    @Input() deleteCallback: (selected: any[]) => void;

    @Input() headerDark: boolean = false;

    @Input() enableHeaderActions: boolean = false;
    @Input() headerActions: { icon: string, name: string, needSelected: boolean, action: (data?: any) => void }[] = [];

    offset: BehaviorSubject<number> = new BehaviorSubject<number>(0);
    size: BehaviorSubject<number> = new BehaviorSubject<number>(10);
    @Input() rowCallbacks: RowCallback[] = [];
    
    public selected = [];
    sizes: { label: string, size: number }[] = [];
    _error: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    toBeExported: any[];
    rows: Page<any[]>;
    activeHeader: HeaderItem;
    public ColumnMode = ColumnMode;
    public SelectionType = SelectionType;

    @ViewChild(DatatableComponent) table: DatatableComponent;
    @ViewChild('detailed') tableRowDetails: any;
    @ViewChild('search', { static: false }) searchInput: any;

    @Output() view: EventEmitter<any> = new EventEmitter<any>();
    id: string;

    searchTerm: string;
    tableData: any[] = [];

    activeSearchTerms: BehaviorSubject<{field: string, header: string, term: string}[]> = new BehaviorSubject<{field: string, header: string, term: string}[]>([]);


    constructor(private _http: HttpClient,
                private router: Router,
                private _modalService: NgbModal) {

        this.id = uuid();

        this.sizes = [
            {label: '5', size: 5},
            {label: '10', size: 10},
            {label: '15', size: 15},
            {label: '20', size: 20},
            {label: '25', size: 25},
        ];
    }

    _loading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);

    get loading() {
        return this._loading.asObservable();
    }

    ngOnChanges(changes: SimpleChanges) {
        this._init()
    }

    onSearch(term: string) {
        let activeSearchTerms = this.activeSearchTerms.value;
        let index = activeSearchTerms.map(search => search.field).indexOf(this.activeHeader.field);
        if(index > -1) {
            activeSearchTerms[index].term = term;
        }
        else {
            activeSearchTerms.push({field: this.activeHeader.field, header: this.activeHeader.header, term: term});

        }
        this.activeSearchTerms.next(activeSearchTerms);
    }

    filterOnSearch() {
        let data = this.mode === 'builder' && this.rows ? this.rows.content : this.data;
        let activeSearchTerms = this.activeSearchTerms.value;
        let filteredData = data;

        for (let i = 0; i < activeSearchTerms.length; i++) {
            let activeSearch = activeSearchTerms[i];
            filteredData = activeSearch.term && activeSearch.term.length > 1 ? data.filter(dataPoint => (dataPoint[activeSearch.field] as string).toLowerCase().includes(activeSearch.term.toLowerCase())) : data;
        }

        this.tableData = filteredData;
    }

    ngAfterViewInit() {
       if(this.toolbar && this.search){
        fromEvent(this.searchInput.nativeElement, "keyup")
        .pipe(
            debounceTime(550),
            filter((e: KeyboardEvent) => e.keyCode === 13),
            map(x => x['target']['value'])
        ).subscribe(term => {
        this.onSearch(term);
        this.searchTerm = null;
    })
       }
    }

    ngOnInit(): void {
        this.activeSearchTerms
            .subscribe(terms => {
            this.filterOnSearch();
        });
        for (let i = 0; i < this.headers.length; i++) {
            let width = getTextWidth(this.headers[i].header, 0.4);
            this.headers[i].width = width;
        }

        this._init();

        this.offset.subscribe(offset => {
            if(this.mode === 'builder')
                this._loadData();
        });

        this.size.subscribe(size => {
            if(this.mode === 'builder')
                this._loadData();
        });
    }

    _init() {
        if (this.headers.length > 0) {
            this.activeHeader = this.headers[0];
        } else {
            this.activeHeader = {
                header: 'Filter',
                field: 'none',
                type: 'text'
            };
        }

        if (this.mode === 'data') {
            this.toBeExported = this.data;
            this.tableData = this.data;
            this.evaluateColumnWidths();
            this._loading.next(false);
        }

        if (this.mode === 'builder') {
            this._loadData();
        }
    }

    _loadData() {
        this._loading.next(true);
        this._error.next(false);
        // let offset = this.offset.getValue();
        // let size = this.size.getValue();
        this.builder(-1, -1, this.builderArgs)
            .subscribe({
                next: page => {
                    this.rows = page;
                    this.toBeExported = page.content;
                    this.tableData = page.content;
                    this.evaluateColumnWidths();
                },
                error: err => {
                    this._error.next(true);
                },
                complete: () => {
                    this._loading.next(false);
                }
            });

    }

    get exportHeaders() {
        return this.headers.map(header => {
            return {
                label: header.header,
                key: header.field
            };
        });
    }

    getColumnWidth(field: string) {
        let index = this.headers.findIndex(header => header.field === field);
        if (index < 0) return 200;
        let width = this.headers[index].width;

        return width;
    }

    evaluateColumnWidths() {
        let data = this.mode === 'builder' ? this.rows.content : this.data;
        data.forEach(row => {
            this.headers.forEach(header => {
                this.evaluateColumnWidth(header.field, row[header.field]);
            });
        });

    }

    evaluateColumnWidth(field: string, data: string) {
        let width = getTextWidth(data, 0.6);
        let index = this.headers.findIndex(header => header.field === field);
        if (this.headers[index].width > width) return;

        this.headers[index].width = width;

    }

    buildParameters(action: RowCallback, row: any) {
        let params: Params = action.link.params;
        if (action.link.passIdWithParams) {
            params.id = row[this.idField];
        }

        return params;
    }

    log(data: any) {
        console.log(data);
        return 'Test';
    }

    rowDetailsToggleExpand(row) {
        this.tableRowDetails.rowDetail.toggleExpandRow(row);
    }

    move(eventRef: any) {

    }

    sort(eventRef: any) {
    }

    switchFilter(header: HeaderItem) {
        this.activeHeader = header;
    }

    execCreate() {
        if (this.create) {
            this.createCallback();
        }
    }

    execDelete() {
        if (this.delete) {
            this.deleteCallback(this.selected);
        }
    }

    onSelect({selected}) {
        this.selected.splice(0, this.selected.length);
        // @ts-ignore
        this.selected.push(...selected);
    }

    doubleClicked(event: any) {
        if (event.type === 'dblclick') {
            this.view.emit(event.row);
        }

    }

    openWarning(modalWarning) {
        this._modalService.open(modalWarning, {
            centered: true,
            windowClass: 'modal modal-warning'
        });
    }

    getProgress(value: number, max: number) {
        let perc = (value / max) * 100;
        return perc < 0 ? 0 : perc;
    }

    getProgressColor(value: number, max: number) {
        let perc = this.getProgress(value, max);

        if (perc < 30) return "danger";
        if (perc < 50) return "warning";
        if (perc < 70) return "info";
        if (perc < 100) return "success";

        return "success";
    }

    refresh() {
        if(this.mode === 'builder') {
            this._loadData();
        }
    }

    updatePageSize($event: {label: string, size: number}) {
        this.size.next($event.size);
    }

    clearSearch() {
        this.activeSearchTerms.next([]);
        this.searchTerm = null;
    }
}
