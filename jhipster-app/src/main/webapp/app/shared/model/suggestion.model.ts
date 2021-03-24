import { IUser } from 'app/core/user/user.model';
import { DayOfWeek } from 'app/shared/model/enumerations/day-of-week.model';
import { Month } from 'app/shared/model/enumerations/month.model';

export interface ISuggestion {
  id?: number;
  dayOfWeek?: DayOfWeek;
  month?: Month;
  day?: number;
  startTime?: string;
  suggestion?: string;
  user?: IUser;
}

export class Suggestion implements ISuggestion {
  constructor(
    public id?: number,
    public dayOfWeek?: DayOfWeek,
    public month?: Month,
    public day?: number,
    public startTime?: string,
    public suggestion?: string,
    public user?: IUser
  ) {}
}
