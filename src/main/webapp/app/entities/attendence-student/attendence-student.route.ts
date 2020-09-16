import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttendenceStudent, AttendenceStudent } from 'app/shared/model/attendence-student.model';
import { AttendenceStudentService } from './attendence-student.service';
import { AttendenceStudentComponent } from './attendence-student.component';
import { AttendenceStudentDetailComponent } from './attendence-student-detail.component';
import { AttendenceStudentUpdateComponent } from './attendence-student-update.component';

@Injectable({ providedIn: 'root' })
export class AttendenceStudentResolve implements Resolve<IAttendenceStudent> {
  constructor(private service: AttendenceStudentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttendenceStudent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attendenceStudent: HttpResponse<AttendenceStudent>) => {
          if (attendenceStudent.body) {
            return of(attendenceStudent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AttendenceStudent());
  }
}

export const attendenceStudentRoute: Routes = [
  {
    path: '',
    component: AttendenceStudentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AttendenceStudents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttendenceStudentDetailComponent,
    resolve: {
      attendenceStudent: AttendenceStudentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AttendenceStudents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttendenceStudentUpdateComponent,
    resolve: {
      attendenceStudent: AttendenceStudentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AttendenceStudents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttendenceStudentUpdateComponent,
    resolve: {
      attendenceStudent: AttendenceStudentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AttendenceStudents',
    },
    canActivate: [UserRouteAccessService],
  },
];
