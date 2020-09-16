import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { AttendenceStudentUpdateComponent } from 'app/entities/attendence-student/attendence-student-update.component';
import { AttendenceStudentService } from 'app/entities/attendence-student/attendence-student.service';
import { AttendenceStudent } from 'app/shared/model/attendence-student.model';

describe('Component Tests', () => {
  describe('AttendenceStudent Management Update Component', () => {
    let comp: AttendenceStudentUpdateComponent;
    let fixture: ComponentFixture<AttendenceStudentUpdateComponent>;
    let service: AttendenceStudentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [AttendenceStudentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AttendenceStudentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendenceStudentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendenceStudentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendenceStudent(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendenceStudent();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
