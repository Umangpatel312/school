import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGradeStudent, GradeStudent } from 'app/shared/model/grade-student.model';
import { GradeStudentService } from './grade-student.service';
import { GradeStudentComponent } from './grade-student.component';
import { GradeStudentDetailComponent } from './grade-student-detail.component';
import { GradeStudentUpdateComponent } from './grade-student-update.component';

@Injectable({ providedIn: 'root' })
export class GradeStudentResolve implements Resolve<IGradeStudent> {
  constructor(private service: GradeStudentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGradeStudent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gradeStudent: HttpResponse<GradeStudent>) => {
          if (gradeStudent.body) {
            return of(gradeStudent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GradeStudent());
  }
}

export const gradeStudentRoute: Routes = [
  {
    path: '',
    component: GradeStudentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeStudents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GradeStudentDetailComponent,
    resolve: {
      gradeStudent: GradeStudentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeStudents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GradeStudentUpdateComponent,
    resolve: {
      gradeStudent: GradeStudentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeStudents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GradeStudentUpdateComponent,
    resolve: {
      gradeStudent: GradeStudentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeStudents',
    },
    canActivate: [UserRouteAccessService],
  },
];
