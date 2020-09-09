import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { AttendenceDateDetailComponent } from 'app/entities/attendence-date/attendence-date-detail.component';
import { AttendenceDate } from 'app/shared/model/attendence-date.model';

describe('Component Tests', () => {
  describe('AttendenceDate Management Detail Component', () => {
    let comp: AttendenceDateDetailComponent;
    let fixture: ComponentFixture<AttendenceDateDetailComponent>;
    const route = ({ data: of({ attendenceDate: new AttendenceDate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [AttendenceDateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AttendenceDateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttendenceDateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load attendenceDate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attendenceDate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
