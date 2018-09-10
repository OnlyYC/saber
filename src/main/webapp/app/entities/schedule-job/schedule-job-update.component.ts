import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IScheduleJob } from 'app/shared/model/schedule-job.model';
import { ScheduleJobService } from './schedule-job.service';

@Component({
    selector: 'jhi-schedule-job-update',
    templateUrl: './schedule-job-update.component.html'
})
export class ScheduleJobUpdateComponent implements OnInit {
    private _scheduleJob: IScheduleJob;
    isSaving: boolean;
    createTime: string;

    constructor(private scheduleJobService: ScheduleJobService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ scheduleJob }) => {
            this.scheduleJob = scheduleJob;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.scheduleJob.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        if (this.scheduleJob.id !== undefined) {
            this.subscribeToSaveResponse(this.scheduleJobService.update(this.scheduleJob));
        } else {
            this.subscribeToSaveResponse(this.scheduleJobService.create(this.scheduleJob));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IScheduleJob>>) {
        result.subscribe((res: HttpResponse<IScheduleJob>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get scheduleJob() {
        return this._scheduleJob;
    }

    set scheduleJob(scheduleJob: IScheduleJob) {
        this._scheduleJob = scheduleJob;
        this.createTime = moment(scheduleJob.createTime).format(DATE_TIME_FORMAT);
    }
}
