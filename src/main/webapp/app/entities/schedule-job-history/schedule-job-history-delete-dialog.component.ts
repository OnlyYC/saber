import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScheduleJobHistory } from 'app/shared/model/schedule-job-history.model';
import { ScheduleJobHistoryService } from './schedule-job-history.service';

@Component({
    selector: 'jhi-schedule-job-history-delete-dialog',
    templateUrl: './schedule-job-history-delete-dialog.component.html'
})
export class ScheduleJobHistoryDeleteDialogComponent {
    scheduleJobHistory: IScheduleJobHistory;

    constructor(
        private scheduleJobHistoryService: ScheduleJobHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.scheduleJobHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'scheduleJobHistoryListModification',
                content: 'Deleted an scheduleJobHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-schedule-job-history-delete-popup',
    template: ''
})
export class ScheduleJobHistoryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ scheduleJobHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ScheduleJobHistoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.scheduleJobHistory = scheduleJobHistory;
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
