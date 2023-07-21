/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Params} from "@angular/router";

export const getTextWidth = (() => {
    const container = document.createElement('canvas');

    return function (inputText?: string | number | null, backupRatio = 0.5): number {
        let text = inputText ?? '';
        text = text.toString();
        let fontSize = parseFloat(
            window.getComputedStyle(document.body).getPropertyValue('font-size')
        );
        return fontSize * backupRatio * text.length;
        // let width = 0;
        //
        // let context = container.getContext('2d');
        //
        // if (context) {
        //     context.font = window
        //         .getComputedStyle(document.body)
        //         .getPropertyValue('font');
        //     width = context.measureText(text).width;
        //     return width * 2;
        // } else {
        //     /* if something goes wrong mounting the canvas, return an estimate calculated using
        //      * the backup ratio, the average open-sans font height-width ratio of 0.5
        //      */
        //
        // }
    };
})();

export interface HeaderItem {
    field: string,
    header: string,
    width?: number,
    type: 'text' | 'status' | 'date' | 'number' | 'blob' | 'enum' | 'progress' | 'tagList' | 'tag',
    isExtra?: boolean
}

export interface RowCallback {
    tag: string,
    icon: string,
    redirect: boolean,
    
    callback: ($event: any, data: any) => void,
    link?: {
        link: string,
        params: Params,
        passIdWithParams: boolean,
    },
}

export interface HeaderEnum {
    value: string;
    color: string;
}

export interface ProgressHeader {
    value: number;
    max: number;
}

export interface ListRef {
    identifier: string;
}

export interface InfoRef {
    identifier: string;
}

export interface SearchRef {
    label: string;
    identifier: string;
}

export class Page<T> {
    first: boolean;
    last: boolean;
    empty: boolean;
    totalPages: number;
    numberOfElements: number;
    totalElements: number;
    size: number;
    number: number;
    content: T[];
    sort: {
        empty: boolean;
        sorted: boolean;
        unsorted: boolean;
    };
    pageable: {
        sort: {
            empty: boolean;
            sorted: boolean;
            unsorted: boolean;
        },
        offset: number;
        pageNumber: number;
        pageSize: 10;
        paged: boolean;
        unpaged: boolean;
    }


    static shell<T>(): Page<T> {
        let page = new Page<T>();
        page.first = true;
        page.last = true;
        page.empty = true;
        page.totalPages = 0;
        page.numberOfElements = 0;
        page.totalElements = 0;
        page.size = 0;
        page.number = 0;
        page.content = [];


        return page;
    }
}
