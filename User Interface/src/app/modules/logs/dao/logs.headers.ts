import {HeaderItem} from "app/modules/app-common/models/app-common.models";

export interface Logs {
}


export const UserLogHeaders: HeaderItem[] = []
export const AccessLogHeader: HeaderItem[] = [
    {
        field: 'email',
        header: 'Email ',
        type: 'text',
    },
    {
        field: 'name',
        header: 'Name',
        type: 'text',
    },
    {
        field: 'result',
        header: 'Result',
        type: 'text',
    },
    {
        field: 'timestamp',
        header: 'Access Time',
        type: 'text',
    },

]

export const EntryLogHeader: HeaderItem[] = []
export const MachineLogHeader: HeaderItem[] = []

export const ShiftLogHeader: HeaderItem[] = []


export const OperationLogHeader: HeaderItem[ ] = [


    {
        header: 'Associated With',
        field: 'associationDesc',
        type: 'text',
    },
    {
        header: 'Action',
        field: 'operation',
        type: 'text'
    },
    {
        header: 'Done By',
        field: 'doneBy',
        type: 'text'
    },
    {
        header: 'Time',
        field: 'timestamp',
        type: 'text'
    },
]