import { Moment } from 'moment';
import { IAttendence } from 'app/shared/model/attendence.model';

export interface IAttendenceDate {
  id?: number;
  date?: Moment;
  attendences?: IAttendence[];
}

export class AttendenceDate implements IAttendenceDate {
  constructor(public id?: number, public date?: Moment, public attendences?: IAttendence[]) {}
}
