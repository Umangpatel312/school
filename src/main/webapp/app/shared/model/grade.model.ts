import { IAttendence } from 'app/shared/model/attendence.model';
import { IGradeStudent } from 'app/shared/model/grade-student.model';

export interface IGrade {
  id?: number;
  grade?: number;
  attendences?: IAttendence[];
  gradeStudents?: IGradeStudent[];
  gradeTeacherId?: number;
}

export class Grade implements IGrade {
  constructor(
    public id?: number,
    public grade?: number,
    public attendences?: IAttendence[],
    public gradeStudents?: IGradeStudent[],
    public gradeTeacherId?: number
  ) {}
}
