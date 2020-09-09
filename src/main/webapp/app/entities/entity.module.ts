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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SchoolEntityModule {}
