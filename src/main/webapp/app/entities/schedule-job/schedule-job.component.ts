import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IScheduleJob } from 'app/shared/model/schedule-job.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ScheduleJobService } from './schedule-job.service';

@Component({
    selector: 'jhi-schedule-job',
    templateUrl: './schedule-job.component.html'
})
export class ScheduleJobComponent implements OnInit, OnDestroy {
    currentAccount: any;
    scheduleJobs: IScheduleJob[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private scheduleJobService: ScheduleJobService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.scheduleJobService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IScheduleJob[]>) => this.paginateScheduleJobs(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/schedule-job'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/schedule-job',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInScheduleJobs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IScheduleJob) {
        return item.id;
    }

    registerChangeInScheduleJobs() {
        this.eventSubscriber = this.eventManager.subscribe('scheduleJobListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    run(id: number) {
        this.scheduleJobService
            .run(id)
            .subscribe(response => {
                this.eventManager.broadcast({
                    name: 'scheduleJobListModification',
                    content: 'resume an scheduleJob'
                });
            });
    }

    pause(id: number) {
        this.scheduleJobService
            .pause(id)
            .subscribe(response => {
                this.eventManager.broadcast({
                    name: 'scheduleJobListModification',
                    content: 'resume an scheduleJob'
                });
            });
    }

    resume(id: number){
        this.scheduleJobService
            .resume(id)
            .subscribe(response => {
                this.eventManager.broadcast({
                    name: 'scheduleJobListModification',
                    content: 'resume an scheduleJob'
                });
            });
    }

    private paginateScheduleJobs(data: IScheduleJob[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.scheduleJobs = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
