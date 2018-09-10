import { Moment } from 'moment';

export interface IScheduleJobHistory {
    id?: number;
    title?: string;
    description?: string;
    beanName?: string;
    methodName?: string;
    params?: string;
    cronExpression?: string;
    status?: number;
    error?: string;
    elapsedTime?: number;
    createTime?: Moment;
    endTime?: Moment;
    scheduleJobId?: number;
}

export class ScheduleJobHistory implements IScheduleJobHistory {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public beanName?: string,
        public methodName?: string,
        public params?: string,
        public cronExpression?: string,
        public status?: number,
        public error?: string,
        public elapsedTime?: number,
        public createTime?: Moment,
        public endTime?: Moment,
        public scheduleJobId?: number
    ) {}
}
