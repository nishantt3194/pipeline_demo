import {ProductSearch, TemplateMinimal, TemplatePayload, VariantSearch} from "app/modules/products/dao/products.models";
import {DatePipe} from '@angular/common';
import {MachineSearch, StationSearch} from "app/modules/machines/dao/machines.models";
import {AreaSearch, CategoryInfo, DowntimeSearch, PrecheckSearch, ShiftSearch} from "../../settings/dao/settings.models";
import {UserSearch} from "../../users/dao/users.models";


export interface Shifts {
}

export interface EntryDateSearch {
    label: string;
    identifier: string;
}

export class NewEntryMetadataRef {

    dates: EntryDateSearch[];
    categories: CategoryInfo[];
    areas: AreaSearch[] = [];
    machines: MachineSearch[] = [];
    stations: StationSearch[] = [];
    shifts: ShiftSearch[] = [];
    products: ProductSearch[] = [];
    variants: VariantSearch[] = [];
    productions: TemplateMinimal[] = [];
    operators: UserSearch[] = [];
    rejections: TemplateMinimal[] = [];
    commonDowntimes: DowntimeSearch[];
    stationSpecificDowntimes: DowntimeSearch[] = [];
    prechecks: PrecheckSearch[] = [];

    constructor() {
        let today: Date = new Date();
        let dates: EntryDateSearch[] = [];
        dates.push({label: "Today", identifier: formatEntryDate(today)});

        for (let i = 1; i < 7; i++) {
            today.setDate(today.getDate() - 1);
            let date = formatEntryDate(today);
            dates.push({label: date, identifier: date});
        }

        this.dates = dates;
    }

}

const formatEntryDate = (date: Date) => {
    const yyyy = date.getFullYear();
    let mm = date.getMonth() + 1;
    let dd = date.getDate();
    let d = dd.toString();
    let m = mm.toString();
    if (dd < 10) d = '0' + dd.toString();
    if (mm < 10) m = '0' + mm.toString();

    return d + '-' + m + '-' + yyyy;
}

export class NewEntryRef {
    id: string;
    shift: string;
    area: string;
    date: string;
    product: string;
    machine: string;
    operator: string;
    variants: string[] = [];
    prechecks: PrecheckRef[]=[];
    downtimes: DowntimeRef[] = [];
    quantities: TemplateRef[] = [];
    remarks: string;
}

export class TemplateRef {
    template: string;
    value?: number;
    calculatedValue?: number;
    type: 'PRODUCTION'| 'REJECTION' | 'TOTAL' | 'EXTRA';
}

export class DowntimeRef {
    reason: string;
    manualCategory: string;
    association: string;

    time: number;

    type: 'COMMON' | 'STATION_SPECIFIC';

    stationName?: string;
    precheck: boolean = false;

    public static from(payload: {
        type: 'COMMON' | 'STATION_SPECIFIC',
        evaluated: boolean,
        parentCategory: string,
        subCategory: string,
        reason: string,
        station: string,
        stationName: string,
        runtimeCategory: string,
        time: number,
    }) {
        let ref = new DowntimeRef();
        ref.type = payload.type;
        ref.reason = payload.reason;
        ref.time = payload.time;
        ref.manualCategory = payload.runtimeCategory;
        ref.association = payload.station;
        ref.stationName = payload.stationName;
        return ref;
    }
}

export class PrecheckRef{

    reason: string;
    time: number;
    public static from(payload: {
        reason: string;
        time: number;}){
            let ref = new PrecheckRef();

            ref.reason = payload.reason;
            ref.time = payload.time;
            return ref;
        }

}

export class NewEntryData {
    id: string = '';
    machine: string = '';
    areas: string[];
    shifts!: string[];
    types!: string[];
    machines!: string[];
    dates: string[];
    products!: string[];
    stations!: string[];
    commonReasons!: string[];
    stationSpecificBreakdowns: string[][] = [];
    plannedReasons!: string[];
    public pTemplates!: any[];
    public rTemplates!: any[];

    constructor(datePipe: DatePipe) {
        let result: string[] = [];
        for (var i = 1; i < 7; i++) {
            var d = new Date();
            d.setDate(d.getDate() - i);
            let val = datePipe.transform(d, 'dd-MM-yyyy');
            if (val)
                result.push(val);
        }

        this.dates = result;
    }
}


export interface ShiftInfo {
    details: Details;
    date: string;
    timestamp: string;
    remarks: string;
    venflon: string;

    totalPDT: number;
    totalUDT: number;
    totalUptime: number;
    totalDowntime: number;

    production: number;
    rejection: number;
    rejectionPercent: number;

    speedFromControlPlan: number;
    actualSpeed: number;

    availability: number;
    performance: number;
    quality: number;
    oee: number;

    machineName: string;

    machineId: string;

    breakdowns: BreakdownInfo[];
    quantities: Quantity[];
    operator: string;
}


export interface Quantity {
    description: string;
    value: number;
}

export interface BreakdownInfo {
    reason: string;
    time: number;
    type: string;
    association: string;
}

export interface Details {
    name: string;
    startTime: string;
    stopTime: string;
    area: string;
}


export class ShiftEntry {
    id: string = '';
    name: string = '';
    date: string = '';
    area: string = '';
    remarks: string = '';
    machine: string = '';
    product: string = '';
    templateChanged: boolean = false;
    downtimeChanged: boolean = false;
    quantityChanged: boolean = false;

    production?: number;
    rejection?: number;
    downtimes: Breakdown[] = [];
    quantities: TemplatePayload[] = [];
    operator: string = '';
}

export interface Breakdown {
    type: string;
    reason: string;
    extra: string;
    time: number;
    association: string;
}

export interface ProductionTableRef{
    description:string,
    components:number
}

export interface EntryWeightedResponse{
    message:string;
    data:string;
}
export interface EntryInfo {
    id:string;
    details: Details;
    date: string;
    timestamp: string;
    remarks: string;
    venflon: string;

    totalPDT: number;
    totalUDT: number;
    totalUptime: number;
    totalDowntime: number;

    production: number;
    rejection: number;
    rejectionPercentage: number;

    speedFromControlPlan: number;
    oee: number;
    machineName: string;
    machineId: string;

    breakdowns: BreakdownInfo[];
    quantities: Quantity[];
    operator: string;
    variant:string[];

    utilizationPercentage:number;
    scheduleProductionTime:number;
    valueOperatingTime:number;
    area: string;
    shift:string;
    goodProduction:number;
    bottleNeckSpeed: number;
    actualSpeed: number;
    
}


