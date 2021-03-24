import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISuggestion } from 'app/shared/model/suggestion.model';

type EntityResponseType = HttpResponse<ISuggestion>;
type EntityArrayResponseType = HttpResponse<ISuggestion[]>;

@Injectable({ providedIn: 'root' })
export class SuggestionService {
  public resourceUrl = SERVER_API_URL + 'api/suggestions';

  constructor(protected http: HttpClient) {}

  create(suggestion: ISuggestion): Observable<EntityResponseType> {
    return this.http.post<ISuggestion>(this.resourceUrl, suggestion, { observe: 'response' });
  }

  update(suggestion: ISuggestion): Observable<EntityResponseType> {
    return this.http.put<ISuggestion>(this.resourceUrl, suggestion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISuggestion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISuggestion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
