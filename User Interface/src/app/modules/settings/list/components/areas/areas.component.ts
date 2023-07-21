import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {NgbModal, NgbModalOptions} from "@ng-bootstrap/ng-bootstrap";
import {HeaderItem} from "app/modules/app-common/models/app-common.models";
import {AreaHeaders} from "app/modules/settings/dao/settings.headers";
import {SettingsService} from "app/modules/settings/services/settings.service";
import {Subject} from "rxjs";
import {NewAreaComponent} from "../../../modals/new-area/new-area.component";

@Component({
    selector: "app-areas",
    templateUrl: "./areas.component.html",
    styleUrls: ["./areas.component.scss"],
})
export class AreasComponent implements OnInit {
    headers: HeaderItem[];
    data: any[];
    private _unsubscribeAll: any;

    constructor(
        private _settingsService: SettingsService,
        private _router: Router,
        public modalService: NgbModal
    ) {
        this.createArea = this.createArea.bind(this);

        this._unsubscribeAll = new Subject();
        this.headers = AreaHeaders;
        this.data = [
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: false,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
            {
                id: "da392e7a-c61d-4f9b-8f4e-eae9ed4a6dac",
                name: "VA-44",
                status: true,
                area: "IVC Molding",
                totalStations: "4 Stations",
            },
        ];
    }

    ngOnInit(): void {
    }

    createArea() {
        const modalOptions: NgbModalOptions = {
            centered: true,
        };

        const modalRef = this.modalService.open(NewAreaComponent, modalOptions);

        modalRef.result.then((result) => {
            if (result.action === "save") {
                let payload: string = result.identifier as string;
                this._settingsService.registerArea(payload).subscribe(
                    (data) => {
                        let currentUrl = this._router.url;
                        this._router.routeReuseStrategy.shouldReuseRoute = () => false;
                        this._router.onSameUrlNavigation = "reload";
                        this._router.navigate([currentUrl]);
                        // this.toastService.show(data, SuccessToastOptions);
                    },
                    (error) => {
                        // this.toastService.show(error.error, FailureToastOptions);
                    }
                );
            }
        });
    }
}
