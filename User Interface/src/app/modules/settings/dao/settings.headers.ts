import {HeaderItem} from "app/modules/app-common/models/app-common.models";

export const AreaHeaders: HeaderItem[] = [
    {
        field: "name",
        header: "Area",
        type: "text",
    },
    {
        field: "status",
        header: "Status",
        type: "status",
    },
    {
        field: "shifts",
        header: "Shift",
        type: "tagList",
    },
    {
        field: "machines",
        header: "Machines",
        type: "text",
    },

    {
        field: "products",
        header: "Products",
        type: "text",
    },
];

export const ShiftsHeaders: HeaderItem[] = [
    {
        field: "name",
        header: " Name",
        type: "text",
    },
    {
        field: "area",
        header: "Area",
        type: "tag",
    },
    {
        field: "startTime",
        header: "Start Time",
        type: "text",
    },
    {
        field: "stopTime",
        header: "Stop Time",
        type: "text",
    },
];
export const DowntimeHeaders: HeaderItem[] = [
    {field: "label", header: "Reason", type: "text"},
    {field: "status", header: "Status", type: "status"},
    {field: 'categoryLabel', header: 'Category', type: 'text'},
    {field: 'type', header: 'Type', type: 'text'},
];
