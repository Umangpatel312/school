import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGradeStudent } from 'app/shared/model/grade-student.model';
import { GradeStudentService } from './grade-student.service';

@Component({
  templateUrl: './grade-student-delete-dialog.component.html',
})
export class GradeStudentDeleteDialogComponent {
  gradeStudent?: IGradeStudent;

  constructor(
    protected gradeStudentService: GradeStudentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gradeStudentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gradeStudentListModification');
      this.activeModal.close();
    });
  }
}
