import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaberSharedModule } from 'app/shared';
import {
    ScheduleJobComponent,
    ScheduleJobDetailComponent,
    ScheduleJobUpdateComponent,
    ScheduleJobDeletePopupComponent,
    ScheduleJobDeleteDialogComponent,
    scheduleJobRoute,
    scheduleJobPopupRoute
} from './';

const ENTITY_STATES = [...scheduleJobRoute, ...scheduleJobPopupRoute];

@NgModule({
    imports: [SaberSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ScheduleJobComponent,
        ScheduleJobDetailComponent,
        ScheduleJobUpdateComponent,
        ScheduleJobDeleteDialogComponent,
        ScheduleJobDeletePopupComponent
    ],
    entryComponents: [ScheduleJobComponent, ScheduleJobUpdateComponent, ScheduleJobDeleteDialogComponent, ScheduleJobDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaberScheduleJobModule {}
