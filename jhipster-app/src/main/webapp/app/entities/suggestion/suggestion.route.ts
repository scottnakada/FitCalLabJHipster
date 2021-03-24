import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISuggestion, Suggestion } from 'app/shared/model/suggestion.model';
import { SuggestionService } from './suggestion.service';
import { SuggestionComponent } from './suggestion.component';
import { SuggestionDetailComponent } from './suggestion-detail.component';
import { SuggestionUpdateComponent } from './suggestion-update.component';

@Injectable({ providedIn: 'root' })
export class SuggestionResolve implements Resolve<ISuggestion> {
  constructor(private service: SuggestionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISuggestion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((suggestion: HttpResponse<Suggestion>) => {
          if (suggestion.body) {
            return of(suggestion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Suggestion());
  }
}

export const suggestionRoute: Routes = [
  {
    path: '',
    component: SuggestionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fitcallabApp.suggestion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SuggestionDetailComponent,
    resolve: {
      suggestion: SuggestionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fitcallabApp.suggestion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SuggestionUpdateComponent,
    resolve: {
      suggestion: SuggestionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fitcallabApp.suggestion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SuggestionUpdateComponent,
    resolve: {
      suggestion: SuggestionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'fitcallabApp.suggestion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
