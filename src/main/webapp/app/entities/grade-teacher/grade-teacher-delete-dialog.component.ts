import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGradeTeacher } from 'app/shared/model/grade-teacher.model';
import { GradeTeacherService } from './grade-teacher.service';

@Component({
  templateUrl: './grade-teacher-delete-dialog.component.html',
})
export class GradeTeacherDeleteDialogComponent {
  gradeTeacher?: IGradeTeacher;

  constructor(
    protected gradeTeacherService: GradeTeacherService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gradeTeacherService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gradeTeacherListModification');
      this.activeModal.close();
    });
  }
}
