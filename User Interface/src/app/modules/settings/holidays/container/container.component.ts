import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { CoreSidebarService } from '@core/components/core-sidebar/core-sidebar.service';
import {CalendarOptions} from '@fullcalendar/angular';
import { HolidaysService } from '../../services/holidays.service';

@Component({
    selector: 'app-container',
    templateUrl: './container.component.html',
    styleUrls: ['./container.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ContainerComponent implements OnInit {
    public calendarOptions: CalendarOptions = {
        headerToolbar: {
            start: 'sidebarToggle, prev,next, title',
            end: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
        },
        initialView: 'dayGridMonth',
        initialEvents: [],
        weekends: true,
        editable: true,
        eventResizableFromStart: true,
        selectable: true,
        selectMirror: true,
        dayMaxEvents: 2,
        navLinks: true,
        // eventClick: this.handleUpdateEventClick.bind(this),
        // eventClassNames: this.eventClass.bind(this),
        // select: this.handleDateSelect.bind(this)
    };

    constructor(
        private _coreSidebarService: CoreSidebarService,
        private _holidayService:HolidaysService
        ) {
            this._holidayService.fetchHolidayslist(0,10).subscribe((data)=>data.content.forEach((val)=>console.log(val)));
    }

    ngOnInit(): void {
    }

    addevent(){
        this._coreSidebarService.getSidebarRegistry("add-event").toggleOpen();
    }


}
