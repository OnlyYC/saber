import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IScheduleJobHistory } from 'app/shared/model/schedule-job-history.model';
import { ScheduleJobHistoryService } from './schedule-job-history.service';
import { IScheduleJob } from 'app/shared/model/schedule-job.model';
import { ScheduleJobService } from 'app/entities/schedule-job';

@Component({
    selector: 'jhi-schedule-job-history-update',
    templateUrl: './schedule-job-history-update.component.html'
})
export class ScheduleJobHistoryUpdateComponent implements OnInit {
    private _scheduleJobHistory: IScheduleJobHistory;
    isSaving: boolean;

    schedulejobs: IScheduleJob[];
    createTime: string;
    endTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private scheduleJobHistoryService: ScheduleJobHistoryService,
        private scheduleJobService: ScheduleJobService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ scheduleJobHistory }) => {
            this.scheduleJobHistory = scheduleJobHistory;
        });
        this.scheduleJobService.query().subscribe(
            (res: HttpResponse<IScheduleJob[]>) => {
                this.schedulejobs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.scheduleJobHistory.createTime = moment(this.createTime, DATE_TIME_FORMAT);
        this.scheduleJobHistory.endTime = moment(this.endTime, DATE_TIME_FORMAT);
        if (this.scheduleJobHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.scheduleJobHistoryService.update(this.scheduleJobHistory));
        } else {
            this.subscribeToSaveResponse(this.scheduleJobHistoryService.create(this.scheduleJobHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IScheduleJobHistory>>) {
        result.subscribe((res: HttpResponse<IScheduleJobHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackScheduleJobById(index: number, item: IScheduleJob) {
        return item.id;
    }
    get scheduleJobHistory() {
        return this._scheduleJobHistory;
    }

    set scheduleJobHistory(scheduleJobHistory: IScheduleJobHistory) {
        this._scheduleJobHistory = scheduleJobHistory;
        this.createTime = moment(scheduleJobHistory.createTime).format(DATE_TIME_FORMAT);
        this.endTime = moment(scheduleJobHistory.endTime).format(DATE_TIME_FORMAT);
    }
}
