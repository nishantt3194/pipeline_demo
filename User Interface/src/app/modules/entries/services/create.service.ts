import {query} from '@angular/animations';
import {Injectable} from '@angular/core';
import {EntryWeightedResponse, NewEntryMetadataRef, NewEntryRef, TemplateRef} from "../dao/shifts.models";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {AreaService} from "../../settings/services/area.service";
import {MachinesService} from "../../machines/services/machines.service";
import {ProductsService} from "../../products/services/products.service";
import {TemplateService} from "../../products/services/template.service";
import {ShiftService} from "../../settings/services/shift.service";
import {UsersService} from "../../users/services/users.service";
import {CategoryService} from "../../settings/services/category.service";
import {StationService} from "../../machines/services/station.service";
import {DowntimeService} from "../../settings/services/downtime.service";
import {environment} from "../../../../environments/environment";
import {EntryRoutes} from "../routes/entries.routes";
import {tap} from "rxjs/operators";
import {DEFAULT_SHIFT_RUNTIME} from "../dao/shifts.data";
import {TemplateMinimal} from "../../products/dao/products.models";
import {EntryInfo} from '../dao/shifts.models';

@Injectable({
    providedIn: 'root'
})
export class CreateService implements Resolve<void> {
    metadata: BehaviorSubject<NewEntryMetadataRef> = new BehaviorSubject<NewEntryMetadataRef>(new NewEntryMetadataRef());
    payload: BehaviorSubject<NewEntryRef> = new BehaviorSubject<NewEntryRef>(new NewEntryRef())
    loading: BehaviorSubject<number[]> = new BehaviorSubject([]);
    saveType: BehaviorSubject<'SAVE' | 'SUBMIT' | 'EDIT'> = new BehaviorSubject<"SAVE" | "SUBMIT" | "EDIT">("SUBMIT");

    constructor(private _http: HttpClient,
                private _areaService: AreaService,
                private _machineService: MachinesService,
                private _productService: ProductsService,
                private _templateService: TemplateService,
                private _shiftService: ShiftService,
                private _userService: UsersService,
                private _categoryService: CategoryService,
                private _stationService: StationService,
                private _downtimeService: DowntimeService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<void> | Promise<void> | void {
        let id = route.queryParams.id;
        let segment = route.url.pop();
        if (segment && segment.path == "edit") {
            this.saveType.next("EDIT");
        } else {
            this.saveType.next("SUBMIT");
        }

        return new Promise<void>((resolve, reject) => {
            Promise.all([id ? this.oldEntryLoaderSequence(id) : this.newEntryLoaderSequence()])
                .then(() => {
                    this.postProcess(this.payload.value, this.metadata.value);
                    resolve();
                }, reject);
        });
    }

    newEntryLoaderSequence = () => [this.refreshAreas(), this.refreshCategories(), this.refreshCommonDowntimes(),];

    oldEntryLoaderSequence = (id) => [this.refreshAreas(), this.refreshCategories(), this.refreshCommonDowntimes(), this.loadEntry(id),];

    loadEntry(id: string) {
        return new Promise((resolve, reject) => {
            this._http.get(environment.apiUrl + EntryRoutes.staged, {params: {id: id, edited: this.saveType.value == 'EDIT'}})
                .pipe(tap((entry: NewEntryRef) => {
                    this.fetchMachines(entry.area);
                    this.fetchProducts(entry.area);
                    this.fetchShifts(entry.area,entry.machine,entry.date);
                    this.fetchProductionTemplates(entry.area, entry.product, "production");
                    this.fetchProductionTemplates(entry.area, entry.product, "rejection");
                    this.refreshStations(entry.machine);
                    this.fetchOperators(entry.machine);
                    this.refreshStationSpecificDowntimes(entry.machine);
                    this.refreshPrechecks(entry.machine);
                }),)
                .subscribe(
                    (entry: NewEntryRef) => {
                        this.payload.next(entry);
                        resolve(entry);
                    }, reject
                )
        });
    }

    refreshAreas() {
        return new Promise((resolve, reject) => {
            this._areaService.search().subscribe(
                areas => {
                    let metadata = this.metadata.value;
                    metadata.areas = areas;

                    this.metadata.next(metadata);

                    resolve(areas);
                }, reject
            )
        })
    }

    refreshCategories() {
        return new Promise((resolve, reject) => {
            this._categoryService.search().subscribe(
                categories => {
                    let metadata = this.metadata.value;
                    metadata.categories = categories;

                    this.metadata.next(metadata);

                    resolve(categories);
                }, reject
            )
        })
    }

    refreshCommonDowntimes() {
        return new Promise((resolve, reject) => {
            this._downtimeService.search().subscribe(
                downtimes => {
                    let metadata = this.metadata.value;
                    metadata.commonDowntimes = downtimes;

                    this.metadata.next(metadata);

                    resolve(downtimes);
                }, reject
            )
        })
    }

    refreshPrechecks(machine: string) {
        this._machineService.getPrechecks(machine).subscribe(
            precheck => {
                let metadata = this.metadata.value;
                metadata.prechecks = precheck;
                
                this.metadata.next(metadata);

            },
        )

    }


    refreshStationSpecificDowntimes(machine: string) {
        this._downtimeService.search(machine).subscribe(
            downtimes => {
                let metadata = this.metadata.value;
                metadata.stationSpecificDowntimes = downtimes;

                this.metadata.next(metadata);
            }
        );
    }


    fetchOperators(machine: string) {
        let queue = this.loading.value;
        queue.push(0);
        this.loading.next(queue);
        this._userService.search(machine)
            .subscribe(operators => {
                let metadata = this.metadata.value;
                metadata.operators = operators;
                this.metadata.next(metadata);
                let queue = this.loading.value;
                queue.pop();
                this.loading.next(queue);
            })
    }

    fetchMachines(area?: string) {
        let queue = this.loading.value;
        queue.push(0);
        this.loading.next(queue);
        this._machineService.search(area)
            .subscribe(machines => {
                let metadata = this.metadata.value;
                metadata.machines = machines;
                this.metadata.next(metadata);
                let queue = this.loading.value;
                queue.pop();
                this.loading.next(queue);
            });
    }

    refreshStations(machine: string) {
        let queue = this.loading.value;
        queue.push(0);
        this.loading.next(queue);
        this._stationService.search(machine)
            .subscribe(stations => {
                let metadata = this.metadata.value;
                metadata.stations = stations;
                this.metadata.next(metadata);
                let queue = this.loading.value;
                queue.pop();
                this.loading.next(queue);
            });
    }

    fetchProducts(area?: string) {
        let queue = this.loading.value;
        queue.push(0);
        this.loading.next(queue);
        this._productService.search(area)
            .subscribe(products => {
                let metadata = this.metadata.value;
                metadata.products = products;
                this.metadata.next(metadata);
                let queue = this.loading.value;
                queue.pop();
                this.loading.next(queue);
            });
    }


    fetchShifts(area:string, machine: string,date:string) {
        let queue = this.loading.value;
        queue.push(0);
        this.loading.next(queue);
        this._shiftService.search(area,machine,date).subscribe(shifts => {
            let metadata = this.metadata.value;
            metadata.shifts = shifts;
            this.metadata.next(metadata);
            let queue = this.loading.value;
            queue.pop();
            this.loading.next(queue);
        });
    }

    fetchProductionTemplates(area: string, product: string, type: 'production' | 'rejection') {
        let queue = this.loading.value;
        queue.push(0);
        this.loading.next(queue);
        this._templateService.search(area, product, type)
            .subscribe(templates => {
                let metadata = this.metadata.value;
                if (type == 'production')
                    metadata.productions = templates;
                else
                    metadata.rejections = templates;

                this.metadata.next(metadata);

                let queue = this.loading.value;
                queue.pop();
                this.loading.next(queue);
            });
    }

    _calculateTotalQuantity(): number {
        let totalBased = false;

        this.metadata.value.productions.forEach(production => {
            if (production.templateType === 'TOTAL') {
                totalBased = true;
            }
        });
        let total = 0;
        let production = 0;
        let rejection = 0;
        if (totalBased) {
            let totalMetadata = this.metadata.value.productions.find(production => production.templateType === 'TOTAL');
            let total = this.payload.value.quantities.find(production => production.type === 'TOTAL');

            production = this._calculateQuantity(totalMetadata, total);
        } else {
            this.payload.value.quantities.filter(quantity => quantity.type === 'PRODUCTION')
                .forEach(quantity => {
                    let metadata = this.metadata.value.productions.find(metadata => metadata.id === quantity.template);
                    if (metadata) {
                        switch (metadata.sign) {
                            case 'ADD': {
                                production += this._calculateQuantity(metadata, quantity);
                                break;
                            }
                            case 'SUB': {
                                production -= this._calculateQuantity(metadata, quantity);
                                break;
                            }
                            default: {
                            }
                        }
                    }
                });

            this.payload.value.quantities.filter(quantity => quantity.type === 'REJECTION' || quantity.type === 'EXTRA').forEach(quantity => {
                let metadata = this.metadata.value.productions.find(metadata => metadata.id === quantity.template);
                if (metadata) {
                    switch (metadata.sign) {
                        case 'ADD': {
                            rejection += this._calculateQuantity(metadata, quantity);
                            break;
                        }
                        case 'SUB': {
                            rejection -= this._calculateQuantity(metadata, quantity);
                            break;
                        }
                        default: {
                        }
                    }
                }
            });
        }

        return totalBased ? production : production + rejection;
    }

    _calculateQuantity(metadata: TemplateMinimal, payload: TemplateRef): number {
        if(!payload){
            return 0;
        }
        return metadata.toConvert ?
            (metadata.operation === 'DIVIDE' ? payload.value / metadata.operand
                : payload.value * metadata.operand) : payload.value;
    }

    evaluate(): {
        message: string, result: boolean, expected: number,
        speed: number,
        totalDowntime: number,
        totalRuntime: number,
        totalQuantity: number,
    } {

        let machine = this.metadata.value.machines.find(machine => machine.identifier === this.payload.value.machine);
        if (!machine) {
            return {
                message: 'Could not find machine. Please re-check the values and submit again.',
                result: false,
                expected: null,
                speed: null,
                totalDowntime: null,
                totalRuntime: null,
                totalQuantity: null,
            }
        }

        let totalDowntime = this.payload.value.downtimes.length < 1 ? 0
            : this.payload.value.downtimes.map(d => d.time).reduce((acc, downtime) => {
                return acc + downtime;
            });

        let totalRuntime = Math.abs(DEFAULT_SHIFT_RUNTIME - totalDowntime);
        let speed = machine.speed;
        let expected = speed * totalRuntime;

        let totalQuantity = this._calculateTotalQuantity();
        if (totalQuantity < expected - machine.tolerance) {
            return {
                message: 'Values entered indicate performance lower than expected. Please re-check the values and submit again.',
                result: false,
                expected: expected,
                speed: speed,
                totalDowntime: totalDowntime,
                totalRuntime: totalRuntime,
                totalQuantity: totalQuantity,

            }

        } else if (totalQuantity > expected + machine.tolerance) {
            return {
                message: 'Values entered indicate performance higher than tolerance value. Please re-check the values and submit again.',
                result: false,
                expected: expected,
                speed: speed,
                totalDowntime: totalDowntime,
                totalRuntime: totalRuntime,
                totalQuantity: totalQuantity,
            }
        }

        return {
            message: 'Data entered is well within expectations. Please click submit to proceed.',
            result: true,
            expected: expected,
            speed: speed,
            totalDowntime: totalDowntime,
            totalRuntime: totalRuntime,
            totalQuantity: totalQuantity,
        }

    }

    submit(saveType: 'SAVE' | 'SUBMIT' | 'EDIT'): Observable<EntryWeightedResponse> {
        let queue = this.loading.value;
        queue.push(0);
        this.loading.next(queue);
        let url = environment.apiUrl + `${EntryRoutes.process}/${saveType}`;

        return this._http.post<EntryWeightedResponse>(url, this.payload.value, {responseType: 'json'})
            .pipe(
                tap((resp) => {
                    queue.pop();
                })
            );

    }

    reset() {
        this.payload.next(new NewEntryRef());
        this.metadata.next(new NewEntryMetadataRef());
        this.loading.next([]);

    }

    private postProcess(payload: NewEntryRef, metadata: NewEntryMetadataRef) {

        let prechecks = metadata.prechecks.map(precheck => precheck.id);
        let stations = metadata.stations.reduce((map, stn) => {
            map[stn.identifier] = stn.label;
            return map;
        }, {});

        payload.downtimes = payload.downtimes.map(downtime => {
            downtime.precheck = prechecks.includes(downtime.reason);
            if (downtime.type === 'STATION_SPECIFIC') {
                downtime.stationName = stations[downtime.association];
            }
            return downtime;
        });


        this.payload.next(payload);
    }
}
