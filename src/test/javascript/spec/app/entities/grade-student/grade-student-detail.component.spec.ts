import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { GradeStudentDetailComponent } from 'app/entities/grade-student/grade-student-detail.component';
import { GradeStudent } from 'app/shared/model/grade-student.model';

describe('Component Tests', () => {
  describe('GradeStudent Management Detail Component', () => {
    let comp: GradeStudentDetailComponent;
    let fixture: ComponentFixture<GradeStudentDetailComponent>;
    const route = ({ data: of({ gradeStudent: new GradeStudent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [GradeStudentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GradeStudentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GradeStudentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gradeStudent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gradeStudent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
