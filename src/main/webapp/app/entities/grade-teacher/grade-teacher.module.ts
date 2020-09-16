import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolSharedModule } from 'app/shared/shared.module';
import { GradeTeacherComponent } from './grade-teacher.component';
import { GradeTeacherDetailComponent } from './grade-teacher-detail.component';
import { GradeTeacherUpdateComponent } from './grade-teacher-update.component';
import { GradeTeacherDeleteDialogComponent } from './grade-teacher-delete-dialog.component';
import { gradeTeacherRoute } from './grade-teacher.route';

@NgModule({
  imports: [SchoolSharedModule, RouterModule.forChild(gradeTeacherRoute)],
  declarations: [GradeTeacherComponent, GradeTeacherDetailComponent, GradeTeacherUpdateComponent, GradeTeacherDeleteDialogComponent],
  entryComponents: [GradeTeacherDeleteDialogComponent],
})
export class SchoolGradeTeacherModule {}
