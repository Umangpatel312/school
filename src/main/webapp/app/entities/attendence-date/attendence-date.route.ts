import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttendenceDate, AttendenceDate } from 'app/shared/model/attendence-date.model';
import { AttendenceDateService } from './attendence-date.service';
import { AttendenceDateComponent } from './attendence-date.component';
import { AttendenceDateDetailComponent } from './attendence-date-detail.component';
import { AttendenceDateUpdateComponent } from './attendence-date-update.component';

@Injectable({ providedIn: 'root' })
export class AttendenceDateResolve implements Resolve<IAttendenceDate> {
  constructor(private service: AttendenceDateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttendenceDate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attendenceDate: HttpResponse<AttendenceDate>) => {
          if (attendenceDate.body) {
            return of(attendenceDate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AttendenceDate());
  }
}

export const attendenceDateRoute: Routes = [
  {
    path: '',
    component: AttendenceDateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AttendenceDates',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttendenceDateDetailComponent,
    resolve: {
      attendenceDate: AttendenceDateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AttendenceDates',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttendenceDateUpdateComponent,
    resolve: {
      attendenceDate: AttendenceDateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AttendenceDates',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttendenceDateUpdateComponent,
    resolve: {
      attendenceDate: AttendenceDateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AttendenceDates',
    },
    canActivate: [UserRouteAccessService],
  },
];
