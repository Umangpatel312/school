import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SchoolSharedModule } from 'app/shared/shared.module';

import { StudentAttendenceComponent } from 'app/query/attendence/student-attendence.component';
import { StudentAttendenceViewComponent } from 'app/query/attendence/student-attendence-view.component';

import { studentAttendenceRoute } from 'app/query/attendence/student-attendence.route';
import { StudentAttendenceDetailComponent } from './student-attendence-detail.component';

@NgModule({
  imports: [SchoolSharedModule, RouterModule.forChild(studentAttendenceRoute)],
  declarations: [StudentAttendenceComponent, StudentAttendenceViewComponent, StudentAttendenceDetailComponent],
})
export class StudentAttendenceModule {}
