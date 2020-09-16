import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolSharedModule } from 'app/shared/shared.module';
import { GradeStudentComponent } from './grade-student.component';
import { GradeStudentDetailComponent } from './grade-student-detail.component';
import { GradeStudentUpdateComponent } from './grade-student-update.component';
import { GradeStudentDeleteDialogComponent } from './grade-student-delete-dialog.component';
import { gradeStudentRoute } from './grade-student.route';

@NgModule({
  imports: [SchoolSharedModule, RouterModule.forChild(gradeStudentRoute)],
  declarations: [GradeStudentComponent, GradeStudentDetailComponent, GradeStudentUpdateComponent, GradeStudentDeleteDialogComponent],
  entryComponents: [GradeStudentDeleteDialogComponent],
})
export class SchoolGradeStudentModule {}
