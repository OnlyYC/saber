import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScheduleJob } from 'app/shared/model/schedule-job.model';
import { ScheduleJobService } from './schedule-job.service';

@Component({
    selector: 'jhi-schedule-job-delete-dialog',
    templateUrl: './schedule-job-delete-dialog.component.html'
})
export class ScheduleJobDeleteDialogComponent {
    scheduleJob: IScheduleJob;

    constructor(
        private scheduleJobService: ScheduleJobService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.scheduleJobService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'scheduleJobListModification',
                content: 'Deleted an scheduleJob'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-schedule-job-delete-popup',
    template: ''
})
export class ScheduleJobDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ scheduleJob }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ScheduleJobDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.scheduleJob = scheduleJob;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
