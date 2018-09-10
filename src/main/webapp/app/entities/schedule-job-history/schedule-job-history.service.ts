import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IScheduleJobHistory } from 'app/shared/model/schedule-job-history.model';

type EntityResponseType = HttpResponse<IScheduleJobHistory>;
type EntityArrayResponseType = HttpResponse<IScheduleJobHistory[]>;

@Injectable({ providedIn: 'root' })
export class ScheduleJobHistoryService {
    private resourceUrl = SERVER_API_URL + 'api/schedule-job-histories';

    constructor(private http: HttpClient) {}

    create(scheduleJobHistory: IScheduleJobHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(scheduleJobHistory);
        return this.http
            .post<IScheduleJobHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(scheduleJobHistory: IScheduleJobHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(scheduleJobHistory);
        return this.http
            .put<IScheduleJobHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IScheduleJobHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IScheduleJobHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(scheduleJobHistory: IScheduleJobHistory): IScheduleJobHistory {
        const copy: IScheduleJobHistory = Object.assign({}, scheduleJobHistory, {
            createTime:
                scheduleJobHistory.createTime != null && scheduleJobHistory.createTime.isValid()
                    ? scheduleJobHistory.createTime.toJSON()
                    : null,
            endTime: scheduleJobHistory.endTime != null && scheduleJobHistory.endTime.isValid() ? scheduleJobHistory.endTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
        res.body.endTime = res.body.endTime != null ? moment(res.body.endTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((scheduleJobHistory: IScheduleJobHistory) => {
            scheduleJobHistory.createTime = scheduleJobHistory.createTime != null ? moment(scheduleJobHistory.createTime) : null;
            scheduleJobHistory.endTime = scheduleJobHistory.endTime != null ? moment(scheduleJobHistory.endTime) : null;
        });
        return res;
    }
}
