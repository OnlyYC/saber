/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SaberTestModule } from '../../../test.module';
import { ScheduleJobHistoryUpdateComponent } from 'app/entities/schedule-job-history/schedule-job-history-update.component';
import { ScheduleJobHistoryService } from 'app/entities/schedule-job-history/schedule-job-history.service';
import { ScheduleJobHistory } from 'app/shared/model/schedule-job-history.model';

describe('Component Tests', () => {
    describe('ScheduleJobHistory Management Update Component', () => {
        let comp: ScheduleJobHistoryUpdateComponent;
        let fixture: ComponentFixture<ScheduleJobHistoryUpdateComponent>;
        let service: ScheduleJobHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaberTestModule],
                declarations: [ScheduleJobHistoryUpdateComponent]
            })
                .overrideTemplate(ScheduleJobHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ScheduleJobHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ScheduleJobHistoryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ScheduleJobHistory(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.scheduleJobHistory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ScheduleJobHistory();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.scheduleJobHistory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
