import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'schedule',
        loadChildren: () => import('./schedule/schedule.module').then(m => m.FitcallabScheduleModule),
      },
      {
        path: 'workout',
        loadChildren: () => import('./workout/workout.module').then(m => m.FitcallabWorkoutModule),
      },
      {
        path: 'suggestion',
        loadChildren: () => import('./suggestion/suggestion.module').then(m => m.FitcallabSuggestionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class FitcallabEntityModule {}
