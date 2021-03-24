import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISuggestion, Suggestion } from 'app/shared/model/suggestion.model';
import { SuggestionService } from './suggestion.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-suggestion-update',
  templateUrl: './suggestion-update.component.html',
})
export class SuggestionUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    dayOfWeek: [null, [Validators.required]],
    month: [null, [Validators.required]],
    day: [null, [Validators.required, Validators.min(1), Validators.max(31)]],
    startTime: [null, [Validators.required, Validators.maxLength(8)]],
    suggestion: [null, [Validators.required, Validators.maxLength(200)]],
    user: [],
  });

  constructor(
    protected suggestionService: SuggestionService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ suggestion }) => {
      this.updateForm(suggestion);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(suggestion: ISuggestion): void {
    this.editForm.patchValue({
      id: suggestion.id,
      dayOfWeek: suggestion.dayOfWeek,
      month: suggestion.month,
      day: suggestion.day,
      startTime: suggestion.startTime,
      suggestion: suggestion.suggestion,
      user: suggestion.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const suggestion = this.createFromForm();
    if (suggestion.id !== undefined) {
      this.subscribeToSaveResponse(this.suggestionService.update(suggestion));
    } else {
      this.subscribeToSaveResponse(this.suggestionService.create(suggestion));
    }
  }

  private createFromForm(): ISuggestion {
    return {
      ...new Suggestion(),
      id: this.editForm.get(['id'])!.value,
      dayOfWeek: this.editForm.get(['dayOfWeek'])!.value,
      month: this.editForm.get(['month'])!.value,
      day: this.editForm.get(['day'])!.value,
      startTime: this.editForm.get(['startTime'])!.value,
      suggestion: this.editForm.get(['suggestion'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuggestion>>): void {
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
