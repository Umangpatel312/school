import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolSharedModule } from 'app/shared/shared.module';
import { AttendenceDateComponent } from './attendence-date.component';
import { AttendenceDateDetailComponent } from './attendence-date-detail.component';
import { AttendenceDateUpdateComponent } from './attendence-date-update.component';
import { AttendenceDateDeleteDialogComponent } from './attendence-date-delete-dialog.component';
import { attendenceDateRoute } from './attendence-date.route';

@NgModule({
  imports: [SchoolSharedModule, RouterModule.forChild(attendenceDateRoute)],
  declarations: [
    AttendenceDateComponent,
    AttendenceDateDetailComponent,
    AttendenceDateUpdateComponent,
    AttendenceDateDeleteDialogComponent,
  ],
  entryComponents: [AttendenceDateDeleteDialogComponent],
})
export class SchoolAttendenceDateModule {}
