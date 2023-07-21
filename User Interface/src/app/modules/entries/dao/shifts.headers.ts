import { HeaderItem } from "app/modules/app-common/models/app-common.models";

export interface Shifts {}

export const ShiftHeaders: HeaderItem[] = [
  {
    field: "shift",
    header: "Shift",
    type: "text",
  },
  {
    field: "date",
    header: "Date",
    type: "text",
  },
  {
    field: "machineName",
    header: "Machine",
    type: "text",
  },
  {
    field: "operator",
    header: "Operator",
    type: "text",
  },
  {
    field: "area",
    header: "Area",
    type: "text",
  },
  {
    field: "product",
    header: "Product",
    type: "text",
  },
];
export const ReportsHeaders: HeaderItem[] = [
  {
    field: "shift",
    header: "Shift",
    type: "text",
  },
  {
    field: "date",
    header: "Date",
    type: "text",
  },
  {
    field: "machineName",
    header: "Machine",
    type: "text",
  },
  {
    field: "operator",
    header: "Operator",
    type: "text",
  },
  {
    field: "area",
    header: "Area",
    type: "text",
  },

  {
    field: "product",
    header: "Product",
    type: "text",
  },
  {
    field: "bottleNeckSpeed",
    header: "BottleNeck Speed",
    type: "text",
    isExtra: true,
  },
  {
    field: "valueOperatingTime",
    header: "Value Operating Time",
    type: "text",
    isExtra: true,
  },
  {
    field: "actualSpeed",
    header: "Actual Speed",
    type: "text",
    isExtra: true,
  },

  {
    field: "production",
    header: "Production",
    type: "number",
    isExtra: true,
  },
  {
    field: "goodProduction",
    header: "Good Production",
    type: "number",
    isExtra: true,
  },
  {
    field: "rejection",
    header: "Rejection",
    type: "number",
    isExtra: true,
  },
  {
    field: "rejectionPercentage",
    header: "Rejection %",
    type: "number",
    isExtra: true,
  },
  {
    field: "utilizationPercentage",
    header: "Utilization %",
    type: "number",
    isExtra: true,
  },
  {
    field: "scheduledProductionTime",
    header: "Schedule Production Time",
    type: "number",
    isExtra: true,
  },

  {
    field: "operator",
    header: "Operator",
    type: "text",
    isExtra: true,
  },
  {
    field: "oee",
    header: "OEE",
    type: "number",
    isExtra: true,
  },

  {
    field: "remarks",
    header: "Remarks",
    type: "text",
    isExtra: true,
  },
];
export const PendingHeaders: HeaderItem[] = [
  {
    field: "shift",
    header: "Shift",
    type: "text",
  },
  {
    field: "date",
    header: "Date",
    type: "text",
  },
  {
    field: "machineName",
    header: "Machine",
    type: "text",
  },
  {
    field: "operator",
    header: "Operator",
    type: "text",
  },
  {
    field: "area",
    header: "Area",
    type: "text",
  },
  {
    field: "product",
    header: "Product",
    type: "text",
  },
];
// export const StagedHeaders: HeaderItem[] = [
//     {
//         field: 'name',
//         header: 'Machine Name',
//         type: 'text',
//     },
//     {
//         field: 'status',
//         header: 'Status',
//         type: 'status',
//     },
//     {
//         field: 'area',
//         header: 'Area',
//         type: 'text',
//     },bottleNeckSpeed: number;
//     {
//         field: 'totalStations',
//         header: 'Stations',
//         type: 'text',
//     },

// ];

export const ProductionHeaders: HeaderItem[] = [
  {
    field: "description",
    header: "Description",
    type: "text",
  },
  {
    field: "components",
    header: "Number of Components",
    type: "number",
  },
];
