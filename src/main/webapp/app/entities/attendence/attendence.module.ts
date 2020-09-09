import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolSharedModule } from 'app/shared/shared.module';
import { AttendenceComponent } from './attendence.component';
import { AttendenceDetailComponent } from './attendence-detail.component';
import { AttendenceUpdateComponent } from './attendence-update.component';
import { AttendenceDeleteDialogComponent } from './attendence-delete-dialog.component';
import { attendenceRoute } from './attendence.route';

@NgModule({
  imports: [SchoolSharedModule, RouterModule.forChild(attendenceRoute)],
  declarations: [AttendenceComponent, AttendenceDetailComponent, AttendenceUpdateComponent, AttendenceDeleteDialogComponent],
  entryComponents: [AttendenceDeleteDialogComponent],
})
export class SchoolAttendenceModule {}
