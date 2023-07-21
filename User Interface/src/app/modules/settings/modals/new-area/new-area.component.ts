import {Component, OnInit} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {NewAreaForm} from "../../dao/settings.models";

@Component({
    selector: "app-new-area",
    templateUrl: "./new-area.component.html",
    styleUrls: ["./new-area.component.scss"],
})
export class NewAreaComponent implements OnInit {
    areaModel: NewAreaForm = new NewAreaForm();

    constructor(public modal: NgbActiveModal) {
    }

    ngOnInit(): void {
    }
}
