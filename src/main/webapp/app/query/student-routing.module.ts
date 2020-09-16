import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'students',
        loadChildren: () => import('./student/student.module').then(m => m.StudentModule),
      },
      {
        path: 'studentAttedence',
        loadChildren: () => import('./attendence/student-attendence.module').then(m => m.StudentAttendenceModule),
      },
    ]),
  ],
})
export class StudentRoutingModule {}
