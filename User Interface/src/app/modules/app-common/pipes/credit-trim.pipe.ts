/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'trimCredits'
})
export class CreditTrimPipe implements PipeTransform {

    transform(number: number, args?: any): any {
        if (isNaN(number)) return null; // will only work value is a number
        if (number === null) return null;
        if (number === 0) return null;
        let abs = Math.abs(number);
        const rounder = Math.pow(10, 1);
        const isNegative = number < 0; // will also work for Negetive numbers
        let key = '';

        const powers = [
            {key: 'q', value: Math.pow(10, 15)},
            {key: 't', value: Math.pow(10, 12)},
            {key: 'b', value: Math.pow(10, 9)},
            {key: 'm', value: Math.pow(10, 6)},
            {key: 'k', value: 1000}
        ];

        for (const element of powers) {
            let reduced = abs / element.value;
            reduced = Math.round(reduced * rounder) / rounder;
            if (reduced >= 1) {
                abs = reduced;
                key = element.key;
                break;
            }
        }
        return (isNegative ? '-' : '') + abs + key;
    }

}
