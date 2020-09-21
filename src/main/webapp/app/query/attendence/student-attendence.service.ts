import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { IAttendenceStudent, AttendenceStudent } from 'app/shared/model/attendence-student.model';
import { Observable } from 'rxjs';
import { IAttendenceDate, AttendenceDate } from 'app/shared/model/attendence-date.model';
import { AuditsQuery } from 'app/admin/audits/audits.service';
import { createRequestOption } from 'app/shared/util/request-util';

type EntityResponseType = HttpResponse<IAttendenceStudent>;
type EntityArrayResponseType = HttpResponse<IAttendenceDate[]>;
@Injectable({ providedIn: 'root' })
export class StudentAttendenceService {
  public resourceUrl = SERVER_API_URL;
  constructor(private http: HttpClient) {}

  getAttendenceByDate(req: AuditsQuery): Observable<HttpResponse<AttendenceDate[]>> {
    const params: HttpParams = createRequestOption(req);
    console.log(params);
    const requestURL = SERVER_API_URL + 'api/getAttendenceDateByTeacher';

    return this.http.get<AttendenceDate[]>(requestURL, {
      params,
      observe: 'response',
    });
  }

  getStudentByDate(date: string): Observable<HttpResponse<AttendenceStudent[]>> {
    console.log(date);
    return this.http.get<AttendenceStudent[]>(SERVER_API_URL + 'api/getAttendenceStudent', {
      params: new HttpParams().set('date', date),
      observe: 'response',
    });
  }
}
