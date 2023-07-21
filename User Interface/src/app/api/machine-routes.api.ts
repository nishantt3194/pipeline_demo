export enum MachineRoutes {

    search = '/machine/search',
    Machine_List = '/machine/list',
    Machine_New = '/machine/create ',
    Machine_Update = 'admin/machines/update',
    Machine_Info = '/machines/info',
    BOS_Data = '/machines/info/bos',
    OEE_Data = '/machines/info/oee/bos',
    Pareto_Data = '/machines/info/pareto',
    OEE_Pareto_Data = '/machines/info/oee/pareto',
    Check_Data = 'data/machines/check-data',
    Toggle_Station_Status = 'admin/machines/station/status',
    Toggle_Machine_Status = 'admin/machines/status/update',
    UtlizationPercentage='/machines/utilization',
    Edit ='/machines/update',
    Station_Search="/machines/stations/search",
    Precheck_Info="/precheck/add",
    Precheck_Info_Edit="/precheck/edit",
    Precheck_Search="/precheck/search",
    Precheck_Delete="/precheck/",
}

export enum ReasonRoute{
    reasonSearch="/downtime/search",
}

export enum Products_Routes {
    Product_List = 'products/list',
    Production_Templates = 'data/templates/production',
    Rejection_Templates = 'data/templates/rejection',
    New_Product = 'admin/products/new',
    Edit_Product = 'admin/products/edit',
}

export enum Shift_Routes {
    Shifts_History = 'data/entry/list/history',
    Shift_Info = 'data/entry/info',
    Shift_Today = 'data/entry/list/today',
    Shift_New = 'admin/shifts/new',
    Shift_Edit = 'admin/shifts/edit',
    Staged_New = 'cache/entry/staged',
    Save_Entry_State = 'cache/entry/save',
    Entry_Edit = 'data/entry/edit',
    Entry_New = 'data/entry/new',
    Entry_Stage = 'data/entry/stage',
    Pending_Today = 'data/entry/list/today/pending',
    Pending_Weekly = 'data/entry/list/history/pending',
}

export enum Settings_Routes {
    Templates = 'settings/templates',
    Save_Templates = 'admin/templates/save',
    Register_Area = 'admin/areas/new',
    Shift_Details_List = 'settings/shifts',
}


