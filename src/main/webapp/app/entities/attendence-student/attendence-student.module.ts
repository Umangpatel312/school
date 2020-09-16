import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolSharedModule } from 'app/shared/shared.module';
import { AttendenceStudentComponent } from './attendence-student.component';
import { AttendenceStudentDetailComponent } from './attendence-student-detail.component';
import { AttendenceStudentUpdateComponent } from './attendence-student-update.component';
import { AttendenceStudentDeleteDialogComponent } from './attendence-student-delete-dialog.component';
import { attendenceStudentRoute } from './attendence-student.route';

@NgModule({
  imports: [SchoolSharedModule, RouterModule.forChild(attendenceStudentRoute)],
  declarations: [
    AttendenceStudentComponent,
    AttendenceStudentDetailComponent,
    AttendenceStudentUpdateComponent,
    AttendenceStudentDeleteDialogComponent,
  ],
  entryComponents: [AttendenceStudentDeleteDialogComponent],
})
export class SchoolAttendenceStudentModule {}
