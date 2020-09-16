import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendenceStudent } from 'app/shared/model/attendence-student.model';
import { AttendenceStudentService } from './attendence-student.service';

@Component({
  templateUrl: './attendence-student-delete-dialog.component.html',
})
export class AttendenceStudentDeleteDialogComponent {
  attendenceStudent?: IAttendenceStudent;

  constructor(
    protected attendenceStudentService: AttendenceStudentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attendenceStudentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attendenceStudentListModification');
      this.activeModal.close();
    });
  }
}
