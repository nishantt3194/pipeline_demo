/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Component, Input} from '@angular/core';

@Component({
    selector: '[core-menu-horizontal-item]',
    templateUrl: './item.component.html'
})
export class CoreMenuHorizontalItemComponent {
    @Input()
    item: any;
}
