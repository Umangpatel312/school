import { Moment } from 'moment';

export interface IAttendenceDate {
  id?: number;
  date?: Moment;
}

export class AttendenceDate implements IAttendenceDate {
  constructor(public id?: number, public date?: Moment) {}
}
