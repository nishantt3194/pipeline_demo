import {ProductionComponent} from "./../components/production/production.component";
import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild, ViewEncapsulation,} from "@angular/core";
import {BehaviorSubject} from "rxjs";
import {DetailsComponent} from "../components/details/details.component";
import Stepper from "bs-stepper";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DowntimesComponent} from "../components/downtimes/downtimes.component";
import {CreateService} from "../../services/create.service";
import {SubmissionConfirmationComponent} from "../modals/submission-confirmation/submission-confirmation.component";
import { ToastrService } from "ngx-toastr";

@Component({
    selector: "app-container",
    templateUrl: "./container.component.html",
    styleUrls: ["./container.component.scss"],
    encapsulation: ViewEncapsulation.None,
})
export class ContainerComponent implements OnInit, AfterViewInit, OnDestroy {
    public isNew: boolean = true;
    totalSteps: number;
    public contentHeader: object;

    loading: boolean = false;

    @ViewChild("wizard_stepper", {static: true}) stepperRef: ElementRef;
    @ViewChild("details", {static: true}) details: DetailsComponent;
    @ViewChild("downtimes", {static: true}) downtimes: DowntimesComponent;
    @ViewChild("production", {static: true}) production: ProductionComponent;
    submitIndex: BehaviorSubject<number> = new BehaviorSubject<number>(0);
    private horizontalWizardStepper: Stepper;
    private bsStepper;
    saveType: string;
    editId:string;

    constructor(
        private _createService: CreateService,
        private _activatedRoute: ActivatedRoute,
        private _modalService: NgbModal,
        private _router: Router,
        private _toastr: ToastrService
    ) {
        this.next = this.next.bind(this);
        this.prev = this.prev.bind(this);
        this.save = this.save.bind(this);
        this.submit = this.submit.bind(this);
        _activatedRoute.queryParams.subscribe((params) => this.saveType = params.saveType)
    }

    private _saving: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(
        false
    );

    get saving() {
        return this._saving.asObservable();
    }

    ngAfterViewInit(): void {
    }

    ngOnInit(): void {
        this._activatedRoute.queryParams.subscribe((params)=>this.editId=params.id);
        this._createService.loading.subscribe(isLoading => {
            this.loading = isLoading.length > 0;
        })

        this.horizontalWizardStepper = new Stepper(
            this.stepperRef.nativeElement,
            {}
        );
        this.bsStepper = document.querySelectorAll("#test-stepper");
    }

    next() {
        this.horizontalWizardStepper.next();
    }

    prev() {
        this.horizontalWizardStepper.previous();
    }

    submit() {

        let modalRef = this._modalService.open(SubmissionConfirmationComponent, {centered: true});
        let evaluation = this._createService.evaluate();
        modalRef.componentInstance.result = evaluation;

        modalRef.result.then((result) => {
            if (result && result.action === 'submit') {
                
                this._createService.submit(this._createService.saveType.value).subscribe((res) => {
                    if(this._createService.saveType.value==='EDIT'){
                        this._router.navigate(["/entries/view"],{queryParams:{id:this.editId}});
                    }else{
                        this._router.navigate(["/entries/view"],{queryParams:{id:res.data}});
                    }
                });
            }
        });
    }

    save() {
        this._createService.submit('SAVE').subscribe(() => {
            this._router.navigate(["/entries/list"]);
            let queue = this._createService.loading.value;
            queue.pop();
            this._createService.loading.next(queue);
        });
    }

    ngOnDestroy() {
        this._createService.reset();
    }
}
