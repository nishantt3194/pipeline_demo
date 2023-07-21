export interface ShiftList {
  shiftdate: Date;
  Shift: string;
  oee: string;
  machine: string;
  area: string;
  
}

export interface DashboardInfo {
  id: string;
  machine: string;
  oee: number;
  production: number;
  downtime: number;
  rejection: number;
  utilizationLoss: number;
  shiftDate: string;
  valurOperatingTime: number;
  utilizationPercentage: number;
  rejectionPercentage: number;
  shiftName: string;
  area: string;
}

export interface EntryMinimal {}

export interface CumulativeDataRef {
  production: number;
  rejection: number;
  oeePercentage: number;
  utilizationPercentage: number;
}

export interface MachineEntryInfo{
  machine:string,
  oee:number ,
  production:number,
  downtime:number,
  rejection:number,
  utilizationLoss:number,
  valurOperatingTime:number,
  utilizationPercentage:number,
  rejectionPercentage:number,
  shiftName:string ,
  shiftDate:string ,
  area:string
}

