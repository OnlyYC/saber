import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaberSharedModule } from 'app/shared';
import {
    ScheduleJobHistoryComponent,
    ScheduleJobHistoryDetailComponent,
    ScheduleJobHistoryUpdateComponent,
    ScheduleJobHistoryDeletePopupComponent,
    ScheduleJobHistoryDeleteDialogComponent,
    scheduleJobHistoryRoute,
    scheduleJobHistoryPopupRoute
} from './';

const ENTITY_STATES = [...scheduleJobHistoryRoute, ...scheduleJobHistoryPopupRoute];

@NgModule({
    imports: [SaberSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ScheduleJobHistoryComponent,
        ScheduleJobHistoryDetailComponent,
        ScheduleJobHistoryUpdateComponent,
        ScheduleJobHistoryDeleteDialogComponent,
        ScheduleJobHistoryDeletePopupComponent
    ],
    entryComponents: [
        ScheduleJobHistoryComponent,
        ScheduleJobHistoryUpdateComponent,
        ScheduleJobHistoryDeleteDialogComponent,
        ScheduleJobHistoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaberScheduleJobHistoryModule {}
