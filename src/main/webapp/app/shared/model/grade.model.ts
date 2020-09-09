export interface IGrade {
  id?: number;
  grade?: number;
  division?: string;
}

export class Grade implements IGrade {
  constructor(public id?: number, public grade?: number, public division?: string) {}
}
