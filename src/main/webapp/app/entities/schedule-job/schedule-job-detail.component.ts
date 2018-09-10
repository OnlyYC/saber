import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScheduleJob } from 'app/shared/model/schedule-job.model';

@Component({
    selector: 'jhi-schedule-job-detail',
    templateUrl: './schedule-job-detail.component.html'
})
export class ScheduleJobDetailComponent implements OnInit {
    scheduleJob: IScheduleJob;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ scheduleJob }) => {
            this.scheduleJob = scheduleJob;
        });
    }

    previousState() {
        window.history.back();
    }
}
