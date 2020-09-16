import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttendenceStudent } from 'app/shared/model/attendence-student.model';

@Component({
  selector: 'jhi-attendence-student-detail',
  templateUrl: './attendence-student-detail.component.html',
})
export class AttendenceStudentDetailComponent implements OnInit {
  attendenceStudent: IAttendenceStudent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendenceStudent }) => (this.attendenceStudent = attendenceStudent));
  }

  previousState(): void {
    window.history.back();
  }
}
