import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FitcallabSharedModule } from 'app/shared/shared.module';
import { SuggestionComponent } from './suggestion.component';
import { SuggestionDetailComponent } from './suggestion-detail.component';
import { SuggestionUpdateComponent } from './suggestion-update.component';
import { SuggestionDeleteDialogComponent } from './suggestion-delete-dialog.component';
import { suggestionRoute } from './suggestion.route';

@NgModule({
  imports: [FitcallabSharedModule, RouterModule.forChild(suggestionRoute)],
  declarations: [SuggestionComponent, SuggestionDetailComponent, SuggestionUpdateComponent, SuggestionDeleteDialogComponent],
  entryComponents: [SuggestionDeleteDialogComponent],
})
export class FitcallabSuggestionModule {}
