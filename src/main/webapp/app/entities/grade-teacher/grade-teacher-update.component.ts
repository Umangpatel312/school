import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGradeTeacher, GradeTeacher } from 'app/shared/model/grade-teacher.model';
import { GradeTeacherService } from './grade-teacher.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IGrade | IUser;

@Component({
  selector: 'jhi-grade-teacher-update',
  templateUrl: './grade-teacher-update.component.html',
})
export class GradeTeacherUpdateComponent implements OnInit {
  isSaving = false;
  grades: IGrade[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    date: [],
    gradeId: [],
    userId: [],
  });

  constructor(
    protected gradeTeacherService: GradeTeacherService,
    protected gradeService: GradeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gradeTeacher }) => {
      if (!gradeTeacher.id) {
        const today = moment().startOf('day');
        gradeTeacher.date = today;
      }

      this.updateForm(gradeTeacher);

      this.gradeService
        .query({ filter: 'gradeteacher-is-null' })
        .pipe(
          map((res: HttpResponse<IGrade[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IGrade[]) => {
          if (!gradeTeacher.gradeId) {
            this.grades = resBody;
          } else {
            this.gradeService
              .find(gradeTeacher.gradeId)
              .pipe(
                map((subRes: HttpResponse<IGrade>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IGrade[]) => (this.grades = concatRes));
          }
        });

      this.userService.queryGetByRole('ROLE_TEACHER').subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(gradeTeacher: IGradeTeacher): void {
    this.editForm.patchValue({
      id: gradeTeacher.id,
      date: gradeTeacher.date ? gradeTeacher.date.format(DATE_TIME_FORMAT) : null,
      gradeId: gradeTeacher.gradeId,
      userId: gradeTeacher.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gradeTeacher = this.createFromForm();
    if (gradeTeacher.id !== undefined) {
      this.subscribeToSaveResponse(this.gradeTeacherService.update(gradeTeacher));
    } else {
      this.subscribeToSaveResponse(this.gradeTeacherService.create(gradeTeacher));
    }
  }

  private createFromForm(): IGradeTeacher {
    return {
      ...new GradeTeacher(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      gradeId: this.editForm.get(['gradeId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGradeTeacher>>): void {
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
