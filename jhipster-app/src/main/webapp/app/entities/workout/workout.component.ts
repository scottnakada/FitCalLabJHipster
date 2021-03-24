import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkout } from 'app/shared/model/workout.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { WorkoutService } from './workout.service';
import { WorkoutDeleteDialogComponent } from './workout-delete-dialog.component';

@Component({
  selector: 'jhi-workout',
  templateUrl: './workout.component.html',
})
export class WorkoutComponent implements OnInit, OnDestroy {
  workouts: IWorkout[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected workoutService: WorkoutService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.workouts = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.workoutService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IWorkout[]>) => this.paginateWorkouts(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.workouts = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkouts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkout): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkouts(): void {
    this.eventSubscriber = this.eventManager.subscribe('workoutListModification', () => this.reset());
  }

  delete(workout: IWorkout): void {
    const modalRef = this.modalService.open(WorkoutDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workout = workout;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateWorkouts(data: IWorkout[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.workouts.push(data[i]);
      }
    }
  }
}
