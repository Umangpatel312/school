import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGradeStudent } from 'app/shared/model/grade-student.model';

@Component({
  selector: 'jhi-grade-student-detail',
  templateUrl: './grade-student-detail.component.html',
})
export class GradeStudentDetailComponent implements OnInit {
  gradeStudent: IGradeStudent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gradeStudent }) => (this.gradeStudent = gradeStudent));
  }

  previousState(): void {
    window.history.back();
  }
}
