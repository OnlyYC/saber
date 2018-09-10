import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ScheduleJob } from 'app/shared/model/schedule-job.model';
import { ScheduleJobService } from './schedule-job.service';
import { ScheduleJobComponent } from './schedule-job.component';
import { ScheduleJobDetailComponent } from './schedule-job-detail.component';
import { ScheduleJobUpdateComponent } from './schedule-job-update.component';
import { ScheduleJobDeletePopupComponent } from './schedule-job-delete-dialog.component';
import { IScheduleJob } from 'app/shared/model/schedule-job.model';

@Injectable({ providedIn: 'root' })
export class ScheduleJobResolve implements Resolve<IScheduleJob> {
    constructor(private service: ScheduleJobService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((scheduleJob: HttpResponse<ScheduleJob>) => scheduleJob.body));
        }
        return of(new ScheduleJob());
    }
}

export const scheduleJobRoute: Routes = [
    {
        path: 'schedule-job',
        component: ScheduleJobComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'saberApp.scheduleJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'schedule-job/:id/view',
        component: ScheduleJobDetailComponent,
        resolve: {
            scheduleJob: ScheduleJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saberApp.scheduleJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'schedule-job/new',
        component: ScheduleJobUpdateComponent,
        resolve: {
            scheduleJob: ScheduleJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saberApp.scheduleJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'schedule-job/:id/edit',
        component: ScheduleJobUpdateComponent,
        resolve: {
            scheduleJob: ScheduleJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saberApp.scheduleJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const scheduleJobPopupRoute: Routes = [
    {
        path: 'schedule-job/:id/delete',
        component: ScheduleJobDeletePopupComponent,
        resolve: {
            scheduleJob: ScheduleJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'saberApp.scheduleJob.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
