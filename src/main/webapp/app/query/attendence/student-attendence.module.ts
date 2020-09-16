import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SchoolSharedModule } from 'app/shared/shared.module';

import { StudentAttendenceComponent } from 'app/query/attendence/student-attendence.component';

import { studentAttendenceRoute } from 'app/query/attendence/student-attendence.route';

@NgModule({
  imports: [SchoolSharedModule, RouterModule.forChild([studentAttendenceRoute])],
  declarations: [StudentAttendenceComponent],
})
export class StudentAttendenceModule {}
