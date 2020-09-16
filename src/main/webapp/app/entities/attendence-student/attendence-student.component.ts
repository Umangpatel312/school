import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttendenceStudent } from 'app/shared/model/attendence-student.model';
import { AttendenceStudentService } from './attendence-student.service';
import { AttendenceStudentDeleteDialogComponent } from './attendence-student-delete-dialog.component';

@Component({
  selector: 'jhi-attendence-student',
  templateUrl: './attendence-student.component.html',
})
export class AttendenceStudentComponent implements OnInit, OnDestroy {
  attendenceStudents?: IAttendenceStudent[];
  eventSubscriber?: Subscription;

  constructor(
    protected attendenceStudentService: AttendenceStudentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.attendenceStudentService
      .query()
      .subscribe((res: HttpResponse<IAttendenceStudent[]>) => (this.attendenceStudents = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAttendenceStudents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttendenceStudent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttendenceStudents(): void {
    this.eventSubscriber = this.eventManager.subscribe('attendenceStudentListModification', () => this.loadAll());
  }

  delete(attendenceStudent: IAttendenceStudent): void {
    const modalRef = this.modalService.open(AttendenceStudentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attendenceStudent = attendenceStudent;
  }
}
