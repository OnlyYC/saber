/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SaberTestModule } from '../../../test.module';
import { ScheduleJobDeleteDialogComponent } from 'app/entities/schedule-job/schedule-job-delete-dialog.component';
import { ScheduleJobService } from 'app/entities/schedule-job/schedule-job.service';

describe('Component Tests', () => {
    describe('ScheduleJob Management Delete Component', () => {
        let comp: ScheduleJobDeleteDialogComponent;
        let fixture: ComponentFixture<ScheduleJobDeleteDialogComponent>;
        let service: ScheduleJobService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaberTestModule],
                declarations: [ScheduleJobDeleteDialogComponent]
            })
                .overrideTemplate(ScheduleJobDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ScheduleJobDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ScheduleJobService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
