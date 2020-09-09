import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAttendenceDate, AttendenceDate } from 'app/shared/model/attendence-date.model';
import { AttendenceDateService } from './attendence-date.service';

@Component({
  selector: 'jhi-attendence-date-update',
  templateUrl: './attendence-date-update.component.html',
})
export class AttendenceDateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    date: [],
  });

  constructor(protected attendenceDateService: AttendenceDateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendenceDate }) => {
      if (!attendenceDate.id) {
        const today = moment().startOf('day');
        attendenceDate.date = today;
      }

      this.updateForm(attendenceDate);
    });
  }

  updateForm(attendenceDate: IAttendenceDate): void {
    this.editForm.patchValue({
      id: attendenceDate.id,
      date: attendenceDate.date ? attendenceDate.date.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attendenceDate = this.createFromForm();
    if (attendenceDate.id !== undefined) {
      this.subscribeToSaveResponse(this.attendenceDateService.update(attendenceDate));
    } else {
      this.subscribeToSaveResponse(this.attendenceDateService.create(attendenceDate));
    }
  }

  private createFromForm(): IAttendenceDate {
    return {
      ...new AttendenceDate(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendenceDate>>): void {
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
}
