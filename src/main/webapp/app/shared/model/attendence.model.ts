export interface IAttendence {
  id?: number;
  gradeId?: number;
  attendenceDateId?: number;
  userLogin?: string;
  userId?: number;
}

export class Attendence implements IAttendence {
  constructor(
    public id?: number,
    public gradeId?: number,
    public attendenceDateId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
