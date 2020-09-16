export interface IAttendenceStudent {
  id?: number;
  marked?: string;
  userLogin?: string;
  userId?: number;
  attendenceId?: number;
}

export class AttendenceStudent implements IAttendenceStudent {
  constructor(
    public id?: number,
    public marked?: string,
    public userLogin?: string,
    public userId?: number,
    public attendenceId?: number
  ) {}
}
