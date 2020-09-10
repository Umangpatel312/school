import { Route } from '@angular/router';

import { StudentComponent } from './student.component';

export const studentRoute: Route = {
  path: ':role',
  component: StudentComponent,
  data: {
    pageTitle: 'User',
    defaultSort: 'id,asc',
  },
};
