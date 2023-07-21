import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MachineRoutes } from 'app/api/machine-routes.api';
import { environment } from 'environments/environment';
import { BOSData, OeeData, OeeParetoData, ParetoData } from '../dao/machines.models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChartService {

  constructor(private _http:HttpClient) { }


  bosChart(startDate:string,endDate:string,id:string):Observable<BOSData>{
    let url= environment.apiUrl+MachineRoutes.BOS_Data;
    return this._http.get<BOSData>(url,{
      
      params:new HttpParams()
                .set("start",startDate)
                .set("end",endDate)
                .set("id",id)
    });
  }

  paretoChart(startDate:string,endDate:string,id:string):Observable<ParetoData>{
    let url= environment.apiUrl+MachineRoutes.Pareto_Data;
    return this._http.get<ParetoData>(url,{
      
      params:new HttpParams()
                .set("start",startDate)
                .set("end",endDate)
                .set("id",id)
    });
  }

  oeeChart(startDate:string,endDate:string,id:string):Observable<OeeData>{
    let url= environment.apiUrl+MachineRoutes.OEE_Data;
    return this._http.get<OeeData>(url,{
      
      params:new HttpParams()
                .set("start",startDate)
                .set("end",endDate)
                .set("id",id)
    });
  }

  oeeParetoChart(start:string,end:string,id:string):Observable<OeeParetoData>{
    let url= environment.apiUrl+MachineRoutes.OEE_Pareto_Data;

    return this._http.get<OeeParetoData>(url,{
      params:new HttpParams()
                .set("start",start)
                .set("end",end)
                .set("id",id)
    })
  }
  
}
