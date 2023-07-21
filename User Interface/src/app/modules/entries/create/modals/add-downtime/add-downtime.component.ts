import {Component, OnDestroy, OnInit} from "@angular/core";
import {CoreSidebarService} from "@core/components/core-sidebar/core-sidebar.service";
import {CreateService} from "../../../services/create.service";
import {DowntimeRef, NewEntryMetadataRef} from "../../../dao/shifts.models";
import {CategoryInfo, DowntimeSearch} from "../../../../settings/dao/settings.models";
import {repeaterAnimation} from "../../../../app-common/models/app-common.animations";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {RequirementPromptComponent} from "../requirement-prompt/requirement-prompt.component";
import Swal from "sweetalert2";
import {CanActivate} from '@angular/router';

@Component({
    selector: "app-add-downtime",
    templateUrl: "./add-downtime.component.html",
    styleUrls: ["./add-downtime.component.scss"],
    animations: [repeaterAnimation]
})
export class AddDowntimeComponent implements OnInit, OnDestroy {

    eva: boolean = true;
    metadata: NewEntryMetadataRef;
    initialMetadata: NewEntryMetadataRef;

    parentCategory: string;

    subCategory: string;

    public commons = [{category: '', subcategory: '', reason: '', time: '',}];
    public specifics = [{reason: '', time: '', station: ''}];


    public types: ('COMMON' | 'STATION_SPECIFIC')[] = ['COMMON'];

    common: boolean = true;

    isStationSpecific: boolean = false;

    downtimes: {
        type: 'COMMON' | 'STATION_SPECIFIC',
        evaluated: boolean,
        parentCategory: string,
        subCategory: string,
        reason: string,
        station: string,
        stationName: string,
        runtimeCategory: string,
        time: number,
    }[] = [];

    constructor(private _coreSidebarService: CoreSidebarService,
                private _createService: CreateService,
                private _modalService: NgbModal) {
    }

    ngOnInit(): void {
        this._createService.metadata.subscribe(metadata => {
            this.metadata = metadata;
            this.metadata.stations=metadata.stations.sort((a,b)=>{
                if(a.label>b.label)
                {return 1;}
                else if(a.label<b.label){
                    return -1;
                }else{
                    return 0;
                }
            });
        });
        this.initialMetadata = JSON.parse(JSON.stringify(this.metadata));

        if (this.downtimes.length === 0)
            this.downtimes = [{
                type: 'COMMON',
                evaluated: false,
                parentCategory: null,
                subCategory: null,
                reason: null,
                station: null,
                stationName: null,
                runtimeCategory: null,
                time: null,
            }];
    }

    ngOnDestroy() {
        this.downtimes = [];
    }


    toggleSidebar() {
        this._coreSidebarService.getSidebarRegistry("add-downtime").toggleOpen();
    }

    type(index: number) {
        return this.types[index];
    }

    submit() {
        let payload: DowntimeRef[] = [];
        this.downtimes.forEach(downtime => {
            payload.push(DowntimeRef.from(downtime));
        });

        let ref = this._createService.payload.value;
        ref.downtimes.push(...payload);
        this._createService.payload.next(ref);
        this.downtimes = [];
        this.toggleSidebar();
    }

    addItem() {
        let evaluated;

        this.downtimes.forEach(downtime => {
            if (evaluated === undefined) evaluated = downtime.evaluated;
            evaluated = evaluated && downtime.evaluated;
        });

        if (evaluated || this.downtimes.length === 0) {
            this.downtimes.push({
                type: 'COMMON',
                evaluated: false,
                parentCategory: null,
                subCategory: null,
                reason: null,
                station: null,
                stationName: null,
                runtimeCategory: null,
                time: null,
            });
        } else {
            Swal.fire({
                title: 'Warning!',
                text: 'Please evaluate all the downtimes before adding a new one',
                icon: 'warning',
                confirmButtonText: 'Ok'
            }).then((result) => {

            });
        }

    }

    deleteItem(i) {
        this.downtimes.splice(i, 1);
    }

    updateType(i: number) {
        this.downtimes[i].type = this.downtimes[i].type === 'COMMON' ? 'STATION_SPECIFIC' : 'COMMON';
       
        if(this.downtimes[i].type==="STATION_SPECIFIC"){
            let categoryInfo=this.metadata.categories;
            let index=categoryInfo.findIndex(obj=>obj.label==="Availability Loss");
            let subCategoryIndex= categoryInfo[index].subcategory.findIndex(obj=>obj.label==="Operational Stops");
            
            this.downtimes[i].parentCategory=categoryInfo[index].identifier;
            this.downtimes[i].subCategory=categoryInfo[index].subcategory[subCategoryIndex].identifier;
        }
    }

    getSubCategories(id: string): CategoryInfo[] {
        let category = this.metadata.categories.find(category => category.identifier === id);
        return category ? category.subcategory : [];
    }

    getReasons(i: number): DowntimeSearch[] {
        let downtime = this.downtimes[i];

        if (this.downtimes[i].type === 'COMMON') {
            return this.metadata.commonDowntimes.filter(common => common.category === downtime.subCategory);
        } else {
            return this.metadata.stationSpecificDowntimes
                .filter(stnSpecific => stnSpecific.association === downtime.station && stnSpecific.category === downtime.subCategory);
        }
    }

    evaluate(index: number) {
        let downtime = this.downtimes[index];
        let parentCategory = this.metadata.categories.find(category => category.identifier === downtime.parentCategory);
        let subCategory = parentCategory.subcategory.find(category => category.identifier === downtime.subCategory);


        if (subCategory) {
            let requirements = subCategory.requirements;
            let fulfilled = false;
            let timedRequirements = requirements.filter((req) => req.type === 'TIME');

            for (let i = 0; i < timedRequirements.length; i++) {
                let timedRequirement = timedRequirements[i];
                if (timedRequirement.thresholdType === 'MIN' && downtime.time >= timedRequirement.value) {
                    fulfilled = true;
                    this.downtimes[index].runtimeCategory = timedRequirement.derivedCategory;
                    break;
                } else if (timedRequirement.thresholdType === 'MAX' && downtime.time <= timedRequirement.value) {
                    fulfilled = true;
                    this.downtimes[index].runtimeCategory = timedRequirement.derivedCategory;
                    break;
                }
            }

            if (!fulfilled) {
                let manualRequirements = requirements.filter((req) => req.type === 'MANUAL');
                if (manualRequirements.length > 0) {
                    let modalRef = this._modalService.open(RequirementPromptComponent, {centered: true})
                    modalRef.componentInstance.requirements = manualRequirements;

                    modalRef.result.then((result) => {
                        if (result && result.action == 'save') {
                            this.downtimes[index].runtimeCategory = result.id;
                            this.downtimes[index].evaluated = true;
                        } else {
                            this.downtimes[index].evaluated = false;
                        }
                    });
                }
            } else {
                this.downtimes[index].evaluated = true;
            }
        }
    }

    resetEvaluation(i: number) {
        this.downtimes[i].evaluated = false;
        this.downtimes[i].reason = null;
        this.downtimes[i].subCategory = null;
        this.downtimes[i].runtimeCategory = null;
    }

    refreshEvaluation(id: any, i: number) {
        let downtime = this.downtimes[i];
        let parentCategory = this.metadata.categories.find(category => category.identifier === downtime.parentCategory);
        let subCategory = parentCategory.subcategory.find(category => category.identifier === downtime.subCategory);
        this.downtimes[i].evaluated = subCategory.requirements.length < 1;
        this.downtimes[i].reason = null;
    }

    recordStationName(id: string, i: number) {
        this.downtimes[i].stationName = this.metadata.stations.filter(station => station.identifier === id)[0].label;
    }
}
