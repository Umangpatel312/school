import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Pagination } from 'app/shared/util/request-util';
import { IUser } from './user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  public resourceUrl = SERVER_API_URL + 'api/users';

  constructor(private http: HttpClient) {}

  create(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(this.resourceUrl, user);
  }

  update(user: IUser): Observable<IUser> {
    return this.http.put<IUser>(this.resourceUrl, user);
  }

  find(login: string): Observable<IUser> {
    return this.http.get<IUser>(`${this.resourceUrl}/${login}`);
  }

  query(req?: Pagination): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryGetByRole(req?: String): Observable<HttpResponse<IUser[]>> {
    // const options = createRequestOption(req);
    return this.http.get<IUser[]>(SERVER_API_URL + `/api/getUserByRole/${req}`, { observe: 'response' });
  }

  delete(login: string): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities');
  }

  getStudent(role: string, req?: Pagination): Observable<HttpResponse<IUser[]>> {
    console.log('data', req);
    const options = createRequestOption(req);
    const url = SERVER_API_URL + `api/studentsAdded/${role}`;
    return this.http.get<IUser[]>(url, { params: options, observe: 'response' });
  }
  // getStudent(role: string): Observable<IUser[]> {
  //   // const options = createRequestOption(req);
  //   console.log(role)
  //   const url = SERVER_API_URL + `api/studentsAdded/${role}`
  //   console.log(url)
  //   return this.http.get<IUser[]>(url);
  // }
}
