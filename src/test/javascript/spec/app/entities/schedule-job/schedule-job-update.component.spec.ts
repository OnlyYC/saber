/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SaberTestModule } from '../../../test.module';
import { ScheduleJobUpdateComponent } from 'app/entities/schedule-job/schedule-job-update.component';
import { ScheduleJobService } from 'app/entities/schedule-job/schedule-job.service';
import { ScheduleJob } from 'app/shared/model/schedule-job.model';

describe('Component Tests', () => {
    describe('ScheduleJob Management Update Component', () => {
        let comp: ScheduleJobUpdateComponent;
        let fixture: ComponentFixture<ScheduleJobUpdateComponent>;
        let service: ScheduleJobService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaberTestModule],
                declarations: [ScheduleJobUpdateComponent]
            })
                .overrideTemplate(ScheduleJobUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ScheduleJobUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ScheduleJobService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ScheduleJob(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.scheduleJob = entity;
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
                    const entity = new ScheduleJob();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.scheduleJob = entity;
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
