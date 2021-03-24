import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISuggestion } from 'app/shared/model/suggestion.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SuggestionService } from './suggestion.service';
import { SuggestionDeleteDialogComponent } from './suggestion-delete-dialog.component';

@Component({
  selector: 'jhi-suggestion',
  templateUrl: './suggestion.component.html',
})
export class SuggestionComponent implements OnInit, OnDestroy {
  suggestions: ISuggestion[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected suggestionService: SuggestionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.suggestions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.suggestionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ISuggestion[]>) => this.paginateSuggestions(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.suggestions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSuggestions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISuggestion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSuggestions(): void {
    this.eventSubscriber = this.eventManager.subscribe('suggestionListModification', () => this.reset());
  }

  delete(suggestion: ISuggestion): void {
    const modalRef = this.modalService.open(SuggestionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.suggestion = suggestion;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSuggestions(data: ISuggestion[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.suggestions.push(data[i]);
      }
    }
  }
}
