import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttendenceStudent, AttendenceStudent } from 'app/shared/model/attendence-student.model';
import { AttendenceStudentService } from './attendence-student.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IAttendence } from 'app/shared/model/attendence.model';
import { AttendenceService } from 'app/entities/attendence/attendence.service';

type SelectableEntity = IUser | IAttendence;

@Component({
  selector: 'jhi-attendence-student-update',
  templateUrl: './attendence-student-update.component.html',
})
export class AttendenceStudentUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  attendences: IAttendence[] = [];

  editForm = this.fb.group({
    id: [],
    marked: [],
    userId: [],
    attendenceId: [],
  });

  constructor(
    protected attendenceStudentService: AttendenceStudentService,
    protected userService: UserService,
    protected attendenceService: AttendenceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendenceStudent }) => {
      this.updateForm(attendenceStudent);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.attendenceService.query().subscribe((res: HttpResponse<IAttendence[]>) => (this.attendences = res.body || []));
    });
  }

  updateForm(attendenceStudent: IAttendenceStudent): void {
    this.editForm.patchValue({
      id: attendenceStudent.id,
      marked: attendenceStudent.marked,
      userId: attendenceStudent.userId,
      attendenceId: attendenceStudent.attendenceId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attendenceStudent = this.createFromForm();
    if (attendenceStudent.id !== undefined) {
      this.subscribeToSaveResponse(this.attendenceStudentService.update(attendenceStudent));
    } else {
      this.subscribeToSaveResponse(this.attendenceStudentService.create(attendenceStudent));
    }
  }

  private createFromForm(): IAttendenceStudent {
    return {
      ...new AttendenceStudent(),
      id: this.editForm.get(['id'])!.value,
      marked: this.editForm.get(['marked'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      attendenceId: this.editForm.get(['attendenceId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendenceStudent>>): void {
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
