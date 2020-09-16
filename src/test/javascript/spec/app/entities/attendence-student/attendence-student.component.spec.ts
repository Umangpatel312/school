import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SchoolTestModule } from '../../../test.module';
import { AttendenceStudentComponent } from 'app/entities/attendence-student/attendence-student.component';
import { AttendenceStudentService } from 'app/entities/attendence-student/attendence-student.service';
import { AttendenceStudent } from 'app/shared/model/attendence-student.model';

describe('Component Tests', () => {
  describe('AttendenceStudent Management Component', () => {
    let comp: AttendenceStudentComponent;
    let fixture: ComponentFixture<AttendenceStudentComponent>;
    let service: AttendenceStudentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [AttendenceStudentComponent],
      })
        .overrideTemplate(AttendenceStudentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendenceStudentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendenceStudentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AttendenceStudent(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.attendenceStudents && comp.attendenceStudents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
