/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {Component, Input} from '@angular/core';

import {CoreMenuItem} from '@core/types';

@Component({
    selector: '[core-menu-vertical-section]',
    templateUrl: './section.component.html'
})
export class CoreMenuVerticalSectionComponent {
    @Input()
    item: CoreMenuItem;
}
