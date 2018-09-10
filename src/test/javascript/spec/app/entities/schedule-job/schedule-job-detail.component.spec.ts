/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaberTestModule } from '../../../test.module';
import { ScheduleJobDetailComponent } from 'app/entities/schedule-job/schedule-job-detail.component';
import { ScheduleJob } from 'app/shared/model/schedule-job.model';

describe('Component Tests', () => {
    describe('ScheduleJob Management Detail Component', () => {
        let comp: ScheduleJobDetailComponent;
        let fixture: ComponentFixture<ScheduleJobDetailComponent>;
        const route = ({ data: of({ scheduleJob: new ScheduleJob(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaberTestModule],
                declarations: [ScheduleJobDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ScheduleJobDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ScheduleJobDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.scheduleJob).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
