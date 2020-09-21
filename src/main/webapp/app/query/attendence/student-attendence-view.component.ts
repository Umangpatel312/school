import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Data, ParamMap } from '@angular/router';
import { DatePipe } from '@angular/common';
import { StudentAttendenceService } from 'app/query/attendence/student-attendence.service';
import { AttendenceDate } from 'app/shared/model/attendence-date.model';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { combineLatest } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-student-attendence-view',
  templateUrl: './student-attendence-view.component.html',
})
export class StudentAttendenceViewComponent implements OnInit {
  attendenceDates?: AttendenceDate[];
  fromDate = '';
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  toDate = '';
  totalItems = 0;

  private dateFormat = 'yyyy-MM-dd';

  constructor(
    private studentAttendenceService: StudentAttendenceService,
    private activatedRoute: ActivatedRoute,
    private datePipe: DatePipe,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.toDate = this.today();
    this.fromDate = this.previousMonth();
    this.handleNavigation();
  }

  canLoad(): boolean {
    return this.fromDate !== '' && this.toDate !== '';
  }

  transition(): void {
    if (this.canLoad()) {
      this.router.navigate(['/query/studentAttendence/view'], {
        queryParams: {
          page: this.page,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
          from: this.fromDate,
          to: this.toDate,
        },
      });
    }
  }

  private previousMonth(): string {
    let date = new Date();
    if (date.getMonth() === 0) {
      date = new Date(date.getFullYear() - 1, 11, date.getDate());
    } else {
      date = new Date(date.getFullYear(), date.getMonth() - 1, date.getDate());
    }
    return this.datePipe.transform(date, this.dateFormat)!;
  }

  private today(): string {
    // Today + 1 day - needed if the current day must be included
    const date = new Date();
    date.setDate(date.getDate() + 1);
    return this.datePipe.transform(date, this.dateFormat)!;
  }

  private handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      this.page = page !== null ? +page : 1;
      console.log(params.get('sort') ?? data['defaultSort']);
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === 'asc';
      if (params.get('from')) {
        this.fromDate = this.datePipe.transform(params.get('from'), this.dateFormat)!;
      }
      if (params.get('to')) {
        this.toDate = this.datePipe.transform(params.get('to'), this.dateFormat)!;
      }
      this.loadData();
    }).subscribe();
  }

  private loadData(): void {
    this.studentAttendenceService
      .getAttendenceByDate({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        fromDate: this.fromDate,
        toDate: this.toDate,
      })
      .subscribe((res: HttpResponse<AttendenceDate[]>) => this.onSuccess(res.body, res.headers));
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(attendenceDates: AttendenceDate[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.attendenceDates = attendenceDates || [];
  }
}
