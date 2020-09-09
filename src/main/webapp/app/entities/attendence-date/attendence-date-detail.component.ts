import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttendenceDate } from 'app/shared/model/attendence-date.model';

@Component({
  selector: 'jhi-attendence-date-detail',
  templateUrl: './attendence-date-detail.component.html',
})
export class AttendenceDateDetailComponent implements OnInit {
  attendenceDate: IAttendenceDate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendenceDate }) => (this.attendenceDate = attendenceDate));
  }

  previousState(): void {
    window.history.back();
  }
}
