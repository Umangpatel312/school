import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { Subscription } from 'rxjs';
import { StudentAttendenceService } from './student-attendence.service';
import { AttendenceStudent } from 'app/shared/model/attendence-student.model';
@Component({
  selector: 'jhi-student-attendence-detail',
  templateUrl: './student-attendence-detail.component.html',
})
export class StudentAttendenceDetailComponent implements OnInit, OnDestroy {
  private dateFormat = 'yyyy-MM-dd';
  filter = '';
  orderProp = 'userLogin';
  reverse = false;
  paramDate = '';
  activatedRouteSubscription?: Subscription;
  attendenceStudent!: AttendenceStudent[];
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    private studentAttendenceService: StudentAttendenceService
  ) {}

  ngOnInit(): void {
    this.activatedRouteSubscription = this.route.params.subscribe(params => {
      // this.paramDate = params['date']
      const paramDate = this.datePipe.transform(params['date'], this.dateFormat)!;
      this.handleNavigation(paramDate);
    });
  }

  private handleNavigation(date: string): void {
    this.studentAttendenceService.getStudentByDate(date).subscribe(response => {
      console.log(response);
      if (response.body != null) this.attendenceStudent = response.body;
    });
  }

  ngOnDestroy(): void {
    this.activatedRouteSubscription?.unsubscribe();
  }
}
