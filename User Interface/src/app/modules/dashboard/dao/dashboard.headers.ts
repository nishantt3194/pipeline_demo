import { HeaderItem } from "../../app-common/models/app-common.models";

export const NewEntriesHeaders: HeaderItem[] = [
  {
    field: "machineName",
    header: "Machine ",
    type: "text",
  },
  {
    field: "area",
    header: "Area",
    type: "text",
  },

  {
    field: "oee",
    header: "OEE%",
    type: "text",
  },
//   {
//     field: "downtime",
//     header: "DownTime",
//     type: "text",
//   },
  {
    field: "date",
    header: "Date",
    type: "text",
  },

];

export const StagedEntriesHeaders: HeaderItem[] = [
  {
    field: "machineName",
    header: "Machine Name",
    type: "text",
  },
  {
    field: "area",
    header: "Area",
    type: "text",
  },
  {
    field: "shift",
    header: "Shift",
    type: "text",
  },
];
