import { IUser } from 'app/core/user/user.model';
import { DayOfWeek } from 'app/shared/model/enumerations/day-of-week.model';
import { Month } from 'app/shared/model/enumerations/month.model';

export interface ISchedule {
  id?: number;
  dayOfWeek?: DayOfWeek;
  month?: Month;
  day?: number;
  startTime?: string;
  duration?: number;
  activity?: string;
  user?: IUser;
}

export class Schedule implements ISchedule {
  constructor(
    public id?: number,
    public dayOfWeek?: DayOfWeek,
    public month?: Month,
    public day?: number,
    public startTime?: string,
    public duration?: number,
    public activity?: string,
    public user?: IUser
  ) {}
}
