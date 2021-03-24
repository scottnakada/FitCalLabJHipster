import { IUser } from 'app/core/user/user.model';

export interface IWorkout {
  id?: number;
  type?: string;
  minutes?: number;
  user?: IUser;
}

export class Workout implements IWorkout {
  constructor(public id?: number, public type?: string, public minutes?: number, public user?: IUser) {}
}
