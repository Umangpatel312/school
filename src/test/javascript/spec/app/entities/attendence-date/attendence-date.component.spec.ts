import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SchoolTestModule } from '../../../test.module';
import { AttendenceDateComponent } from 'app/entities/attendence-date/attendence-date.component';
import { AttendenceDateService } from 'app/entities/attendence-date/attendence-date.service';
import { AttendenceDate } from 'app/shared/model/attendence-date.model';

describe('Component Tests', () => {
  describe('AttendenceDate Management Component', () => {
    let comp: AttendenceDateComponent;
    let fixture: ComponentFixture<AttendenceDateComponent>;
    let service: AttendenceDateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [AttendenceDateComponent],
      })
        .overrideTemplate(AttendenceDateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendenceDateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendenceDateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AttendenceDate(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.attendenceDates && comp.attendenceDates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
