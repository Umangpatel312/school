import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendenceDate } from 'app/shared/model/attendence-date.model';

type EntityResponseType = HttpResponse<IAttendenceDate>;
type EntityArrayResponseType = HttpResponse<IAttendenceDate[]>;

@Injectable({ providedIn: 'root' })
export class AttendenceDateService {
  public resourceUrl = SERVER_API_URL + 'api/attendence-dates';

  constructor(protected http: HttpClient) {}

  create(attendenceDate: IAttendenceDate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendenceDate);
    return this.http
      .post<IAttendenceDate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(attendenceDate: IAttendenceDate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendenceDate);
    return this.http
      .put<IAttendenceDate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAttendenceDate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAttendenceDate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(attendenceDate: IAttendenceDate): IAttendenceDate {
    const copy: IAttendenceDate = Object.assign({}, attendenceDate, {
      date: attendenceDate.date && attendenceDate.date.isValid() ? attendenceDate.date.toJSON() : undefined,
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
      res.body.forEach((attendenceDate: IAttendenceDate) => {
        attendenceDate.date = attendenceDate.date ? moment(attendenceDate.date) : undefined;
      });
    }
    return res;
  }
}
