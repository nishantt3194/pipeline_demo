export interface MachineSearch {
  identifier: string;
  label: string;
  production: number;
  tolerance: number;
  speed: number;
}

export interface StationSearch {
  identifier: string;
  label: string;
}

export interface MachineMinimalRef {
  id: string;
  name: string;
  status: boolean;
  area: string;
  totalStation: number;
}

// export class NewMachinePayload {
//     name: string = '';
//     area: string = '';
//     speed?: number;
//     tolerance?: number;
//     prodLimit?: number;
//     lsa?: number;
//     stretched?: number;
//     stations: string[] = ['Station 1'];
// }
// export class UpdateMachinePayload {
//     identifier: string;
//     lsa: number;
//     stretched: number;
//     prodLimit: number;
//     tolerance: number;
//     speed: number;

//     constructor(identifier: string, lsa: number, stretched: number, prodLimit: number, tolerance: number, speed: number) {
//         this.identifier = identifier;
//         this.lsa = lsa;
//         this.stretched = stretched;
//         this.prodLimit = prodLimit;
//         this.tolerance = tolerance;
//         this.speed = speed;
//     }
// }

export interface MachineInfo {
  id: string;
  name: string;
  status: boolean;
  stretchedTarget: number;
  lsaTarget: number;
  area: string;
  oeeTarget: number;
  baseValue: number;
  operator: string;
  prodLimit: number;
  tolerance: number;
  speed: number;
  shifts: MinShift[];
  stations: StationInfo[];
  precheck: PrecheckInfo[];
}

export interface StationInfo {
  label: string;
  identifier: string;
}
export interface PrecheckInfo {
  id: string;
  machine: string;

  reason: string;
  reaosnId: string;
  time: number;
}

export class EditMachineRef {
  id?: string;
  stretchedTarget?: number;
  lsaTarget?: number;
  prodLimit?: number;
  tolerance?: number;
  speed?: number;
  oeeTarget?: number;
  baseValue?: number;
}

export interface ReasonSearch {
  label: string;
  category: string;
  type: string;
  status: boolean;
  identifier: string;
  association: string;
  categoryLabel: string;
}
export class EditPrecheckRef {
  id?: string;
  machine?: string;
  reason?: string;
  time?: number;
}
export interface ShiftHistory {
  date: string;
  name: string;
  avail: string;
  perf: string;
  quality: string;
  oee: string;
  machineName: string;
  production: string;
}

export interface MinShift {}

// export class AssignOperator {
//     email: string;
//     machines: string[];

//     constructor(email: string, machines: string[]) {
//         this.email = email;
//         this.machines = machines;
//     }
// }

// export interface BOSData {
//     labels: string[];
//     goodQuantities: number[];
//     weekly: number[];
//     weekly4: number[];
//     weekly12: number[];
//     lsa: number[];
//     stretched: number[];
// }

export class BOSTableItem {
  date: string;
  weekly: number;
  weekly4: number;
  weekly12: number;
  lsa: number;
  stretched: number;

  constructor(
    date: string,
    weekly: number,
    weekly4: number,
    weekly12: number,
    lsa: number,
    stretched: number
  ) {
    this.date = date;
    this.weekly = weekly;
    this.weekly4 = weekly4;
    this.weekly12 = weekly12;
    this.lsa = lsa;
    this.stretched = stretched;
  }
}

export interface BOSData {
  labels: string[];
  goodQuantities: number[];
  weekly: number[];
  weekly4: number[];
  weekly12: number[];
  lsa: number[];
  stretched: number[];
}

export interface ParetoData {
  reasons: string[];
  downtime: number[];
  percentage: number[];
  cumulativePercentage: number[];
  shifts: number;
}

export interface OeeData {
  labels: string[];
  weekly: number[];
  weekly4: number[];
  weekly12: number[];
  lsa: number[];
  oeeTarget: number[];
  baseValue: number[];
}

export interface OeeParetoData {
  category: string[];
  downtime: number[];
  percentage: number[];
  cumulativePercentage: number[];
  shifts: number;
}
