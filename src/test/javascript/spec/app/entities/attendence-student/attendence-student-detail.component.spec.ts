import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { AttendenceStudentDetailComponent } from 'app/entities/attendence-student/attendence-student-detail.component';
import { AttendenceStudent } from 'app/shared/model/attendence-student.model';

describe('Component Tests', () => {
  describe('AttendenceStudent Management Detail Component', () => {
    let comp: AttendenceStudentDetailComponent;
    let fixture: ComponentFixture<AttendenceStudentDetailComponent>;
    const route = ({ data: of({ attendenceStudent: new AttendenceStudent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [AttendenceStudentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AttendenceStudentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttendenceStudentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load attendenceStudent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attendenceStudent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
