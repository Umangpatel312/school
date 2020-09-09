import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendenceDate } from 'app/shared/model/attendence-date.model';
import { AttendenceDateService } from './attendence-date.service';

@Component({
  templateUrl: './attendence-date-delete-dialog.component.html',
})
export class AttendenceDateDeleteDialogComponent {
  attendenceDate?: IAttendenceDate;

  constructor(
    protected attendenceDateService: AttendenceDateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attendenceDateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attendenceDateListModification');
      this.activeModal.close();
    });
  }
}
