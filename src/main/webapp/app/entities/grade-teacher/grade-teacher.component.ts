import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGradeTeacher } from 'app/shared/model/grade-teacher.model';
import { GradeTeacherService } from './grade-teacher.service';
import { GradeTeacherDeleteDialogComponent } from './grade-teacher-delete-dialog.component';

@Component({
  selector: 'jhi-grade-teacher',
  templateUrl: './grade-teacher.component.html',
})
export class GradeTeacherComponent implements OnInit, OnDestroy {
  gradeTeachers?: IGradeTeacher[];
  eventSubscriber?: Subscription;

  constructor(
    protected gradeTeacherService: GradeTeacherService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.gradeTeacherService.query().subscribe((res: HttpResponse<IGradeTeacher[]>) => (this.gradeTeachers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGradeTeachers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGradeTeacher): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGradeTeachers(): void {
    this.eventSubscriber = this.eventManager.subscribe('gradeTeacherListModification', () => this.loadAll());
  }

  delete(gradeTeacher: IGradeTeacher): void {
    const modalRef = this.modalService.open(GradeTeacherDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gradeTeacher = gradeTeacher;
  }
}
