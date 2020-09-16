import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGradeStudent, GradeStudent } from 'app/shared/model/grade-student.model';
import { GradeStudentService } from './grade-student.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';

type SelectableEntity = IUser | IGrade;

@Component({
  selector: 'jhi-grade-student-update',
  templateUrl: './grade-student-update.component.html',
})
export class GradeStudentUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  grades: IGrade[] = [];

  editForm = this.fb.group({
    id: [],
    userId: [],
    gradeId: [],
  });

  constructor(
    protected gradeStudentService: GradeStudentService,
    protected userService: UserService,
    protected gradeService: GradeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gradeStudent }) => {
      this.updateForm(gradeStudent);

      this.userService.queryGetByRole('ROLE_USER').subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.gradeService.query().subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));
    });
  }

  updateForm(gradeStudent: IGradeStudent): void {
    this.editForm.patchValue({
      id: gradeStudent.id,
      userId: gradeStudent.userId,
      gradeId: gradeStudent.gradeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gradeStudent = this.createFromForm();
    if (gradeStudent.id !== undefined) {
      this.subscribeToSaveResponse(this.gradeStudentService.update(gradeStudent));
    } else {
      this.subscribeToSaveResponse(this.gradeStudentService.create(gradeStudent));
    }
  }

  private createFromForm(): IGradeStudent {
    return {
      ...new GradeStudent(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      gradeId: this.editForm.get(['gradeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGradeStudent>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
