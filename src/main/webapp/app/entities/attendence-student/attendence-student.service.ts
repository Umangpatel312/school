import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendenceStudent } from 'app/shared/model/attendence-student.model';

type EntityResponseType = HttpResponse<IAttendenceStudent>;
type EntityArrayResponseType = HttpResponse<IAttendenceStudent[]>;

@Injectable({ providedIn: 'root' })
export class AttendenceStudentService {
  public resourceUrl = SERVER_API_URL + 'api/attendence-students';

  constructor(protected http: HttpClient) {}

  create(attendenceStudent: IAttendenceStudent): Observable<EntityResponseType> {
    return this.http.post<IAttendenceStudent>(this.resourceUrl, attendenceStudent, { observe: 'response' });
  }

  update(attendenceStudent: IAttendenceStudent): Observable<EntityResponseType> {
    return this.http.put<IAttendenceStudent>(this.resourceUrl, attendenceStudent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttendenceStudent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttendenceStudent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  takeAttendence(attendenceStudent: IAttendenceStudent[]): Observable<EntityArrayResponseType> {
    return this.http.post<IAttendenceStudent[]>(SERVER_API_URL + 'api/takeAttendence-student', attendenceStudent, { observe: 'response' });
  }
}
