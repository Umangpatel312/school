import { Moment } from 'moment';

export interface IGradeTeacher {
  id?: number;
  date?: Moment;
  gradeGrade?: string;
  gradeId?: number;
  userLogin?: string;
  userId?: number;
}

export class GradeTeacher implements IGradeTeacher {
  constructor(
    public id?: number,
    public date?: Moment,
    public gradeGrade?: string,
    public gradeId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
