import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SuggestionService } from 'app/entities/suggestion/suggestion.service';
import { ISuggestion, Suggestion } from 'app/shared/model/suggestion.model';
import { DayOfWeek } from 'app/shared/model/enumerations/day-of-week.model';
import { Month } from 'app/shared/model/enumerations/month.model';

describe('Service Tests', () => {
  describe('Suggestion Service', () => {
    let injector: TestBed;
    let service: SuggestionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISuggestion;
    let expectedResult: ISuggestion | ISuggestion[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SuggestionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Suggestion(0, DayOfWeek.Mon, Month.Jan, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Suggestion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Suggestion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Suggestion', () => {
        const returnedFromService = Object.assign(
          {
            dayOfWeek: 'BBBBBB',
            month: 'BBBBBB',
            day: 1,
            startTime: 'BBBBBB',
            suggestion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Suggestion', () => {
        const returnedFromService = Object.assign(
          {
            dayOfWeek: 'BBBBBB',
            month: 'BBBBBB',
            day: 1,
            startTime: 'BBBBBB',
            suggestion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Suggestion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
