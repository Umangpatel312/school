import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { GradeTeacherUpdateComponent } from 'app/entities/grade-teacher/grade-teacher-update.component';
import { GradeTeacherService } from 'app/entities/grade-teacher/grade-teacher.service';
import { GradeTeacher } from 'app/shared/model/grade-teacher.model';

describe('Component Tests', () => {
  describe('GradeTeacher Management Update Component', () => {
    let comp: GradeTeacherUpdateComponent;
    let fixture: ComponentFixture<GradeTeacherUpdateComponent>;
    let service: GradeTeacherService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [GradeTeacherUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GradeTeacherUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GradeTeacherUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GradeTeacherService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GradeTeacher(123);
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
        const entity = new GradeTeacher();
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
