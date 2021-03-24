import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISchedule, Schedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-schedule-update',
  templateUrl: './schedule-update.component.html',
})
export class ScheduleUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    dayOfWeek: [null, [Validators.required]],
    month: [null, [Validators.required]],
    day: [null, [Validators.required, Validators.min(1), Validators.max(31)]],
    startTime: [null, [Validators.required, Validators.maxLength(8)]],
    duration: [null, [Validators.required, Validators.min(1), Validators.max(120)]],
    activity: [null, [Validators.required, Validators.maxLength(100)]],
    user: [],
  });

  constructor(
    protected scheduleService: ScheduleService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schedule }) => {
      this.updateForm(schedule);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(schedule: ISchedule): void {
    this.editForm.patchValue({
      id: schedule.id,
      dayOfWeek: schedule.dayOfWeek,
      month: schedule.month,
      day: schedule.day,
      startTime: schedule.startTime,
      duration: schedule.duration,
      activity: schedule.activity,
      user: schedule.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schedule = this.createFromForm();
    if (schedule.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleService.update(schedule));
    } else {
      this.subscribeToSaveResponse(this.scheduleService.create(schedule));
    }
  }

  private createFromForm(): ISchedule {
    return {
      ...new Schedule(),
      id: this.editForm.get(['id'])!.value,
      dayOfWeek: this.editForm.get(['dayOfWeek'])!.value,
      month: this.editForm.get(['month'])!.value,
      day: this.editForm.get(['day'])!.value,
      startTime: this.editForm.get(['startTime'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      activity: this.editForm.get(['activity'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchedule>>): void {
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
