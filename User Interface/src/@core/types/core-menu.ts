/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

// prettier-ignore
export interface CoreMenuItem {
    id: string;
    title: string;
    url?: string;
    type: 'section' | 'collapsible' | 'item';
    role?: Array<string>;
    translate?: string;
    icon?: string;

    customIcon?: boolean;
    disabled?: boolean;
    hidden?: boolean;
    classes?: string;
    exactMatch?: boolean;
    externalUrl?: boolean;
    openInNewTab?: boolean;
    badge?: {
        title?: string;
        translate?: string;
        classes?: string;
    };
    children?: CoreMenuItem[];
}

export interface CoreMenu extends CoreMenuItem {
    children?: CoreMenuItem[];
}
