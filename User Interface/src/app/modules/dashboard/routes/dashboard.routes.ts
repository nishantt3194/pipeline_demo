export interface Dashboard {
}


export enum Dashboard_Routes{
    Top_Performing_Machine = '/dashboard/top/machine',
    Last_Five_Entries = '/dashboard/operator/last/five/entry',
    Last_Staged_Entry = '/dashboard/operator/staged/entry',
}
 export enum Supervisor_Routes{
    Top_Performing_Machines = "/dashboard/super/top/machines",
    Top_Performing_Machines_inArea="/dashboard/top/machine",
    Last_Three_Entries = '/dashboard/super/three/entry',
    Data = '/dashboard/super/total/data'
 }
