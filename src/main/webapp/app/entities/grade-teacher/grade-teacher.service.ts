import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGradeTeacher } from 'app/shared/model/grade-teacher.model';

type EntityResponseType = HttpResponse<IGradeTeacher>;
type EntityArrayResponseType = HttpResponse<IGradeTeacher[]>;

@Injectable({ providedIn: 'root' })
export class GradeTeacherService {
  public resourceUrl = SERVER_API_URL + 'api/grade-teachers';

  constructor(protected http: HttpClient) {}

  create(gradeTeacher: IGradeTeacher): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gradeTeacher);
    return this.http
      .post<IGradeTeacher>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(gradeTeacher: IGradeTeacher): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gradeTeacher);
    return this.http
      .put<IGradeTeacher>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGradeTeacher>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGradeTeacher[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(gradeTeacher: IGradeTeacher): IGradeTeacher {
    const copy: IGradeTeacher = Object.assign({}, gradeTeacher, {
      date: gradeTeacher.date && gradeTeacher.date.isValid() ? gradeTeacher.date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((gradeTeacher: IGradeTeacher) => {
        gradeTeacher.date = gradeTeacher.date ? moment(gradeTeacher.date) : undefined;
      });
    }
    return res;
  }
}
