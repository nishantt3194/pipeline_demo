export interface DowntimeMinimal {
  id: string;
  reason: string;
  status: boolean;
  category: string;
  type: "COMMON" | "STATION_SPECIFIC";
  association: string;
}
export interface DowntimeSearch {
  label: string;
  identifier: string;
  type: string;
  status: boolean;
  category: string;
  association: string;
  categoryLabel: string;
}

export interface DowntimeStatus{
  id:string;
  status:boolean;
}

export interface PrecheckSearch {
  id: string;
  machine: string;
  reason: string;
  reasonId: string;
  time: number;
}
export interface CategoryInfo {
  label: string;
  identifier: string;
  subcategory: CategoryInfo[];
  requirements: CategoryRequirement[];
}

export interface CategoryRequirement {
  prompt: string;
  parentCategory: string;
  derivedCategory: string;
  type: "MANUAL" | "TIME";

  thresholdType: "MIN" | "MAX" | "DEFAULT";
  value: number;
}

export interface ShiftSearch {
  label: string;
  identifier: string;
}

export interface AreaSearch {
  identifier: string;
  label: string;
}

export class NewShiftPayload {
  id: string;
  name: string;
  area: string;
  startTime: string;
  stopTime: string;
}

export interface ShiftDetail {
  id: string;
  name: string;
  area: string;
  startTime: string;
  stopTime: string;
}
export class EditShiftRef {
  id?: string;
  name?: string;
  area?: string;
  startTime?: string;
  stopTime?: string;
}

export class NewAreaForm {
  name: string = "";
}

export interface AreaDetail {
  id: string;
  name: string;
  status: boolean;
  machine: number;
  shifts: string[];
  products: number;
}

export class NewHolidaysPayload {
  name: string = "";
  desc: string = "";
  date: string = "";
}

export interface HolidaysInfo {
  title: string;
  date: string;
}

export interface MachineSearch {
  label: string;
  identifier: string;
}
