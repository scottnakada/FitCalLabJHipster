import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FitcallabTestModule } from '../../../test.module';
import { SuggestionDetailComponent } from 'app/entities/suggestion/suggestion-detail.component';
import { Suggestion } from 'app/shared/model/suggestion.model';

describe('Component Tests', () => {
  describe('Suggestion Management Detail Component', () => {
    let comp: SuggestionDetailComponent;
    let fixture: ComponentFixture<SuggestionDetailComponent>;
    const route = ({ data: of({ suggestion: new Suggestion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FitcallabTestModule],
        declarations: [SuggestionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SuggestionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SuggestionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load suggestion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.suggestion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
