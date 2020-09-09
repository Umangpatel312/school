import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttendenceDate } from 'app/shared/model/attendence-date.model';
import { AttendenceDateService } from './attendence-date.service';
import { AttendenceDateDeleteDialogComponent } from './attendence-date-delete-dialog.component';

@Component({
  selector: 'jhi-attendence-date',
  templateUrl: './attendence-date.component.html',
})
export class AttendenceDateComponent implements OnInit, OnDestroy {
  attendenceDates?: IAttendenceDate[];
  eventSubscriber?: Subscription;

  constructor(
    protected attendenceDateService: AttendenceDateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.attendenceDateService.query().subscribe((res: HttpResponse<IAttendenceDate[]>) => (this.attendenceDates = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAttendenceDates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttendenceDate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttendenceDates(): void {
    this.eventSubscriber = this.eventManager.subscribe('attendenceDateListModification', () => this.loadAll());
  }

  delete(attendenceDate: IAttendenceDate): void {
    const modalRef = this.modalService.open(AttendenceDateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attendenceDate = attendenceDate;
  }
}
