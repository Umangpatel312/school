import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { GradeTeacherDetailComponent } from 'app/entities/grade-teacher/grade-teacher-detail.component';
import { GradeTeacher } from 'app/shared/model/grade-teacher.model';

describe('Component Tests', () => {
  describe('GradeTeacher Management Detail Component', () => {
    let comp: GradeTeacherDetailComponent;
    let fixture: ComponentFixture<GradeTeacherDetailComponent>;
    const route = ({ data: of({ gradeTeacher: new GradeTeacher(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [GradeTeacherDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GradeTeacherDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GradeTeacherDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gradeTeacher on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gradeTeacher).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
