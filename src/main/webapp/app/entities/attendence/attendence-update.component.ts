import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttendence, Attendence } from 'app/shared/model/attendence.model';
import { AttendenceService } from './attendence.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';
import { IAttendenceDate } from 'app/shared/model/attendence-date.model';
import { AttendenceDateService } from 'app/entities/attendence-date/attendence-date.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IGrade | IAttendenceDate | IUser;

@Component({
  selector: 'jhi-attendence-update',
  templateUrl: './attendence-update.component.html',
})
export class AttendenceUpdateComponent implements OnInit {
  isSaving = false;
  grades: IGrade[] = [];
  attendencedates: IAttendenceDate[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    gradeId: [],
    attendenceDateId: [],
    userId: [],
  });

  constructor(
    protected attendenceService: AttendenceService,
    protected gradeService: GradeService,
    protected attendenceDateService: AttendenceDateService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendence }) => {
      this.updateForm(attendence);

      this.gradeService.query().subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));

      this.attendenceDateService.query().subscribe((res: HttpResponse<IAttendenceDate[]>) => (this.attendencedates = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(attendence: IAttendence): void {
    this.editForm.patchValue({
      id: attendence.id,
      gradeId: attendence.gradeId,
      attendenceDateId: attendence.attendenceDateId,
      userId: attendence.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attendence = this.createFromForm();
    if (attendence.id !== undefined) {
      this.subscribeToSaveResponse(this.attendenceService.update(attendence));
    } else {
      this.subscribeToSaveResponse(this.attendenceService.create(attendence));
    }
  }

  private createFromForm(): IAttendence {
    return {
      ...new Attendence(),
      id: this.editForm.get(['id'])!.value,
      gradeId: this.editForm.get(['gradeId'])!.value,
      attendenceDateId: this.editForm.get(['attendenceDateId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendence>>): void {
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
