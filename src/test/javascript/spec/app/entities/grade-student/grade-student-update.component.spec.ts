import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { GradeStudentUpdateComponent } from 'app/entities/grade-student/grade-student-update.component';
import { GradeStudentService } from 'app/entities/grade-student/grade-student.service';
import { GradeStudent } from 'app/shared/model/grade-student.model';

describe('Component Tests', () => {
  describe('GradeStudent Management Update Component', () => {
    let comp: GradeStudentUpdateComponent;
    let fixture: ComponentFixture<GradeStudentUpdateComponent>;
    let service: GradeStudentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [GradeStudentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GradeStudentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GradeStudentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GradeStudentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GradeStudent(123);
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
        const entity = new GradeStudent();
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
