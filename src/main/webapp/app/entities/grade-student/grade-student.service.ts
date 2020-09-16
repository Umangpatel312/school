import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGradeStudent } from 'app/shared/model/grade-student.model';

type EntityResponseType = HttpResponse<IGradeStudent>;
type EntityArrayResponseType = HttpResponse<IGradeStudent[]>;

@Injectable({ providedIn: 'root' })
export class GradeStudentService {
  public resourceUrl = SERVER_API_URL + 'api/grade-students';

  constructor(protected http: HttpClient) {}

  create(gradeStudent: IGradeStudent): Observable<EntityResponseType> {
    return this.http.post<IGradeStudent>(this.resourceUrl, gradeStudent, { observe: 'response' });
  }

  update(gradeStudent: IGradeStudent): Observable<EntityResponseType> {
    return this.http.put<IGradeStudent>(this.resourceUrl, gradeStudent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGradeStudent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGradeStudent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getStudentByTeacher(): Observable<EntityArrayResponseType> {
    return this.http.get<IGradeStudent[]>(SERVER_API_URL + 'api/getStudentByTeacher', { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
