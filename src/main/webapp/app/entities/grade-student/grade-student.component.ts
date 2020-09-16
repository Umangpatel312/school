import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGradeStudent } from 'app/shared/model/grade-student.model';
import { GradeStudentService } from './grade-student.service';
import { GradeStudentDeleteDialogComponent } from './grade-student-delete-dialog.component';

@Component({
  selector: 'jhi-grade-student',
  templateUrl: './grade-student.component.html',
})
export class GradeStudentComponent implements OnInit, OnDestroy {
  gradeStudents?: IGradeStudent[];
  eventSubscriber?: Subscription;

  constructor(
    protected gradeStudentService: GradeStudentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.gradeStudentService.query().subscribe((res: HttpResponse<IGradeStudent[]>) => (this.gradeStudents = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGradeStudents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGradeStudent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGradeStudents(): void {
    this.eventSubscriber = this.eventManager.subscribe('gradeStudentListModification', () => this.loadAll());
  }

  delete(gradeStudent: IGradeStudent): void {
    const modalRef = this.modalService.open(GradeStudentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gradeStudent = gradeStudent;
  }
}
