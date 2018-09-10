import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IScheduleJob } from 'app/shared/model/schedule-job.model';

type EntityResponseType = HttpResponse<IScheduleJob>;
type EntityArrayResponseType = HttpResponse<IScheduleJob[]>;

@Injectable({ providedIn: 'root' })
export class ScheduleJobService {
    private resourceUrl = SERVER_API_URL + 'api/schedule-jobs';

    constructor(private http: HttpClient) {}

    create(scheduleJob: IScheduleJob): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(scheduleJob);
        return this.http
            .post<IScheduleJob>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(scheduleJob: IScheduleJob): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(scheduleJob);
        return this.http
            .put<IScheduleJob>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IScheduleJob>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IScheduleJob[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    run(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/run/${id}`, { observe: 'response' });
    }

    pause(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/pause/${id}`, { observe: 'response' });
    }

    resume(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/resume/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(scheduleJob: IScheduleJob): IScheduleJob {
        const copy: IScheduleJob = Object.assign({}, scheduleJob, {
            createTime: scheduleJob.createTime != null && scheduleJob.createTime.isValid() ? scheduleJob.createTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((scheduleJob: IScheduleJob) => {
            scheduleJob.createTime = scheduleJob.createTime != null ? moment(scheduleJob.createTime) : null;
        });
        return res;
    }
}
