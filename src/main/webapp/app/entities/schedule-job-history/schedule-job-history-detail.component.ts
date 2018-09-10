import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScheduleJobHistory } from 'app/shared/model/schedule-job-history.model';

@Component({
    selector: 'jhi-schedule-job-history-detail',
    templateUrl: './schedule-job-history-detail.component.html'
})
export class ScheduleJobHistoryDetailComponent implements OnInit {
    scheduleJobHistory: IScheduleJobHistory;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ scheduleJobHistory }) => {
            this.scheduleJobHistory = scheduleJobHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
