/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {animate, style, transition, trigger} from '@angular/animations';

export const repeaterAnimation = trigger('heightIn', [
    transition(':enter', [
        style({opacity: '0', height: '0px'}),
        animate('.2s ease-out', style({opacity: '1', height: '*'})),
    ]),
]);
