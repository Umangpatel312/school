export interface IGradeStudent {
  id?: number;
  userLogin?: string;
  userId?: number;
  gradeGrade?: string;
  gradeId?: number;
}

export class GradeStudent implements IGradeStudent {
  constructor(public id?: number, public userLogin?: string, public userId?: number, public gradeGrade?: string, public gradeId?: number) {}
}
