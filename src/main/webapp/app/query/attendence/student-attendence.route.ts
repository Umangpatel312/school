import { Route } from '@angular/router';

import { StudentAttendenceComponent } from 'app/query/attendence/student-attendence.component';

export const studentAttendenceRoute: Route = {
  path: '',
  component: StudentAttendenceComponent,
  data: {
    pageTitle: 'Student Attendence',
    defaultSort: 'id,asc',
  },
};
