import { Routes } from '@angular/router';

import { StudentAttendenceComponent } from 'app/query/attendence/student-attendence.component';
import { StudentAttendenceViewComponent } from './student-attendence-view.component';
import { StudentAttendenceDetailComponent } from 'app/query/attendence/student-attendence-detail.component';
export const studentAttendenceRoute: Routes = [
  {
    path: '',
    component: StudentAttendenceComponent,
    data: {
      pageTitle: 'Student Attendence',
      defaultSort: 'id,asc',
    },
  },
  {
    path: 'view',
    component: StudentAttendenceViewComponent,
    data: {
      pageTitle: 'View Attendences of Date',
      defaultSort: 'id,asc',
    },
  },
  {
    path: 'detail/:date',
    component: StudentAttendenceDetailComponent,
  },
];
