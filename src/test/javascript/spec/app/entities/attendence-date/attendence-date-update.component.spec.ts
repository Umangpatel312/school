import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SchoolTestModule } from '../../../test.module';
import { AttendenceDateUpdateComponent } from 'app/entities/attendence-date/attendence-date-update.component';
import { AttendenceDateService } from 'app/entities/attendence-date/attendence-date.service';
import { AttendenceDate } from 'app/shared/model/attendence-date.model';

describe('Component Tests', () => {
  describe('AttendenceDate Management Update Component', () => {
    let comp: AttendenceDateUpdateComponent;
    let fixture: ComponentFixture<AttendenceDateUpdateComponent>;
    let service: AttendenceDateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolTestModule],
        declarations: [AttendenceDateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AttendenceDateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendenceDateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendenceDateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendenceDate(123);
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
        const entity = new AttendenceDate();
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
