import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGradeTeacher, GradeTeacher } from 'app/shared/model/grade-teacher.model';
import { GradeTeacherService } from './grade-teacher.service';
import { GradeTeacherComponent } from './grade-teacher.component';
import { GradeTeacherDetailComponent } from './grade-teacher-detail.component';
import { GradeTeacherUpdateComponent } from './grade-teacher-update.component';

@Injectable({ providedIn: 'root' })
export class GradeTeacherResolve implements Resolve<IGradeTeacher> {
  constructor(private service: GradeTeacherService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGradeTeacher> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gradeTeacher: HttpResponse<GradeTeacher>) => {
          if (gradeTeacher.body) {
            return of(gradeTeacher.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GradeTeacher());
  }
}

export const gradeTeacherRoute: Routes = [
  {
    path: '',
    component: GradeTeacherComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeTeachers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GradeTeacherDetailComponent,
    resolve: {
      gradeTeacher: GradeTeacherResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeTeachers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GradeTeacherUpdateComponent,
    resolve: {
      gradeTeacher: GradeTeacherResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeTeachers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GradeTeacherUpdateComponent,
    resolve: {
      gradeTeacher: GradeTeacherResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GradeTeachers',
    },
    canActivate: [UserRouteAccessService],
  },
];
