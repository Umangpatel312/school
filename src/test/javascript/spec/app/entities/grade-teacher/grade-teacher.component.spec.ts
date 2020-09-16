import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SchoolTestModule } from '../../../test.module';
import { GradeTeacherComponent } from 'app/entities/grade-teacher/grade-teacher.component';
import { GradeTeacherService } from 'app/entities/grade-teacher/grade-teacher.service';
import { GradeTeacher } from 'app/shared/model/grade-teacher.model';

describe('Component Tests', () => {
  describe('GradeTeacher Management Component', () => {
    let comp: GradeTeacherComponent;
    let fixture: ComponentFixture<GradeTeacherComponent>;
    let service: GradeTeacherService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [GradeTeacherComponent],
      })
        .overrideTemplate(GradeTeacherComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GradeTeacherComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GradeTeacherService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GradeTeacher(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gradeTeachers && comp.gradeTeachers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
