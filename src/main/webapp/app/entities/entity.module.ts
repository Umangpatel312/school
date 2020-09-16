import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'attendence-date',
        loadChildren: () => import('./attendence-date/attendence-date.module').then(m => m.SchoolAttendenceDateModule),
      },
      {
        path: 'grade',
        loadChildren: () => import('./grade/grade.module').then(m => m.SchoolGradeModule),
      },
      {
        path: 'attendence',
        loadChildren: () => import('./attendence/attendence.module').then(m => m.SchoolAttendenceModule),
      },
      {
        path: 'grade-student',
        loadChildren: () => import('./grade-student/grade-student.module').then(m => m.SchoolGradeStudentModule),
      },
      {
        path: 'grade-teacher',
        loadChildren: () => import('./grade-teacher/grade-teacher.module').then(m => m.SchoolGradeTeacherModule),
      },
      {
        path: 'attendence-student',
        loadChildren: () => import('./attendence-student/attendence-student.module').then(m => m.SchoolAttendenceStudentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SchoolEntityModule {}
