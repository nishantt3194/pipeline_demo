/*
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

import {HeaderItem} from 'app/modules/app-common/models/app-common.models';

export const MachineHeaders: HeaderItem[] = [
    {
        field: 'name',
        header: 'Machine Name',
        type: 'text',
    },
    {
        field: 'status',
        header: 'Status',
        type: 'status',
    },
    {
        field: 'area',
        header: 'Area',
        type: 'tag',
    },
    {
        field: 'totalStation',
        header: 'Stations',
        type: 'text',
    },

];

export const ShiftHeaders: HeaderItem[] = [
    {
        header: 'Shift Date',
        field: 'date',
        type: 'text',
    },
    {
        header: 'Shift',
        field: 'name',
        type: 'text',
    },
    {
        header: 'Availability',
        field: 'avail',
        type: 'text',
    },
    {
        header: 'Performance',
        field: 'perf',
        type: 'text',
    },
    {
        header: 'Quality',
        field: 'quality',
        type: 'text',
    },
    {
        header: 'OEE',
        field: 'oee',
        type: 'text',
    },
    {
        header: 'Machine',
        field: 'machineName',
        type: 'text',
    },
    {
        header: 'Production',
        field: 'production',
        type: 'text',
    },
    // {
    //     header: 'Rejection',
    //     field: 'rejection',
    //     type: 'text',
    // },

    // {
    //     header: 'Downtime',
    //     field: 'downtime',
    //     type: 'text',
    // },
    // {
    //     header: 'Breakdowns',
    //     field: 'breakdowns',
    //     type: 'text',
    // },

    // {
    //     header: 'Speed',
    //     field: 'speed',
    //     type: 'text',
    // },
    // {
    //     header: 'Operator',
    //     field: 'operator',
    //     type: 'text',
    // }


];
