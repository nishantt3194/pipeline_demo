/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'striphtml'
})
export class StripHtmlPipe implements PipeTransform {
    transform(value: string): any {
        return value.replace(/<.*?>/g, ''); // replace tags
    }
}
