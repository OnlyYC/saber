import { Moment } from 'moment';

export interface IScheduleJob {
    id?: number;
    title?: string;
    description?: string;
    beanName?: string;
    methodName?: string;
    params?: string;
    cronExpression?: string;
    status?: number;
    remark?: string;
    createTime?: Moment;
}

export class ScheduleJob implements IScheduleJob {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public beanName?: string,
        public methodName?: string,
        public params?: string,
        public cronExpression?: string,
        public status?: number,
        public remark?: string,
        public createTime?: Moment
    ) {}
}
