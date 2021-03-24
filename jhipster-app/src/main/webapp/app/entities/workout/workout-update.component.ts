import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkout, Workout } from 'app/shared/model/workout.model';
import { WorkoutService } from './workout.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-workout-update',
  templateUrl: './workout-update.component.html',
})
export class WorkoutUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required, Validators.maxLength(20)]],
    minutes: [null, [Validators.required, Validators.min(1), Validators.max(120)]],
    user: [],
  });

  constructor(
    protected workoutService: WorkoutService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workout }) => {
      this.updateForm(workout);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(workout: IWorkout): void {
    this.editForm.patchValue({
      id: workout.id,
      type: workout.type,
      minutes: workout.minutes,
      user: workout.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workout = this.createFromForm();
    if (workout.id !== undefined) {
      this.subscribeToSaveResponse(this.workoutService.update(workout));
    } else {
      this.subscribeToSaveResponse(this.workoutService.create(workout));
    }
  }

  private createFromForm(): IWorkout {
    return {
      ...new Workout(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      minutes: this.editForm.get(['minutes'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkout>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
