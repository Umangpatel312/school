import { IAttendenceStudent } from 'app/shared/model/attendence-student.model';

export interface IAttendence {
  id?: number;
  attendenceStudents?: IAttendenceStudent[];
  userLogin?: string;
  userId?: number;
  gradeGrade?: string;
  gradeId?: number;
  attendenceDateDate?: string;
  attendenceDateId?: number;
}

export class Attendence implements IAttendence {
  constructor(
    public id?: number,
    public attendenceStudents?: IAttendenceStudent[],
    public userLogin?: string,
    public userId?: number,
    public gradeGrade?: string,
    public gradeId?: number,
    public attendenceDateDate?: string,
    public attendenceDateId?: number
  ) {}
}
