import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGradeTeacher } from 'app/shared/model/grade-teacher.model';

@Component({
  selector: 'jhi-grade-teacher-detail',
  templateUrl: './grade-teacher-detail.component.html',
})
export class GradeTeacherDetailComponent implements OnInit {
  gradeTeacher: IGradeTeacher | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gradeTeacher }) => (this.gradeTeacher = gradeTeacher));
  }

  previousState(): void {
    window.history.back();
  }
}
