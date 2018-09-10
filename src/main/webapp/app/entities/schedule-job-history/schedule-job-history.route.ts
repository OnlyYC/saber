import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ScheduleJobHistory } from 'app/shared/model/schedule-job-history.model';
import { ScheduleJobHistoryService } from './schedule-job-history.service';
import { ScheduleJobHistoryComponent } from './schedule-job-history.component';
import { ScheduleJobHistoryDetailComponent } from './schedule-job-history-detail.component';
import { ScheduleJobHistoryUpdateComponent } from './schedule-job-history-update.component';
import { ScheduleJobHistoryDeletePopupComponent } from './schedule-job-history-delete-dialog.component';
import { IScheduleJobHistory } from 'app/shared/model/schedule-job-history.model';

@Injectable({ providedIn: 'root' })
export class ScheduleJobHistoryResolve implements Resolve<IScheduleJobHistory> {
    constructor(private service: ScheduleJobHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((scheduleJobHistory: HttpResponse<ScheduleJobHistory>) => scheduleJobHistory.body));
        }
        return of(new ScheduleJobHistory());
    }
}

export const scheduleJobHistoryRoute: Routes = [
    {
        path: 'schedule-job-history',
        component: ScheduleJobHistoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saberApp.scheduleJobHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'schedule-job-history/:id/view',
        component: ScheduleJobHistoryDetailComponent,
        resolve: {
            scheduleJobHistory: ScheduleJobHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saberApp.scheduleJobHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'schedule-job-history/new',
        component: ScheduleJobHistoryUpdateComponent,
        resolve: {
            scheduleJobHistory: ScheduleJobHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saberApp.scheduleJobHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'schedule-job-history/:id/edit',
        component: ScheduleJobHistoryUpdateComponent,
        resolve: {
            scheduleJobHistory: ScheduleJobHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saberApp.scheduleJobHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const scheduleJobHistoryPopupRoute: Routes = [
    {
        path: 'schedule-job-history/:id/delete',
        component: ScheduleJobHistoryDeletePopupComponent,
        resolve: {
            scheduleJobHistory: ScheduleJobHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saberApp.scheduleJobHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
