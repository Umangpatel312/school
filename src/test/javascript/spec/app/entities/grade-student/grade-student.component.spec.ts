import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SchoolTestModule } from '../../../test.module';
import { GradeStudentComponent } from 'app/entities/grade-student/grade-student.component';
import { GradeStudentService } from 'app/entities/grade-student/grade-student.service';
import { GradeStudent } from 'app/shared/model/grade-student.model';

describe('Component Tests', () => {
  describe('GradeStudent Management Component', () => {
    let comp: GradeStudentComponent;
    let fixture: ComponentFixture<GradeStudentComponent>;
    let service: GradeStudentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [GradeStudentComponent],
      })
        .overrideTemplate(GradeStudentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GradeStudentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GradeStudentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GradeStudent(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gradeStudents && comp.gradeStudents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
