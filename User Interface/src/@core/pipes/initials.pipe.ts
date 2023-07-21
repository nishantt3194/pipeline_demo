/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'initials'
})
export class InitialsPipe implements PipeTransform {
    transform(fullName: string): any {
        return fullName
            ?.split(' ')
            .map(n => n[0])
            .join('');
    }
}
