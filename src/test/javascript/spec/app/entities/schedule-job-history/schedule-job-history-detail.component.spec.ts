/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaberTestModule } from '../../../test.module';
import { ScheduleJobHistoryDetailComponent } from 'app/entities/schedule-job-history/schedule-job-history-detail.component';
import { ScheduleJobHistory } from 'app/shared/model/schedule-job-history.model';

describe('Component Tests', () => {
    describe('ScheduleJobHistory Management Detail Component', () => {
        let comp: ScheduleJobHistoryDetailComponent;
        let fixture: ComponentFixture<ScheduleJobHistoryDetailComponent>;
        const route = ({ data: of({ scheduleJobHistory: new ScheduleJobHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SaberTestModule],
                declarations: [ScheduleJobHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ScheduleJobHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ScheduleJobHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.scheduleJobHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
