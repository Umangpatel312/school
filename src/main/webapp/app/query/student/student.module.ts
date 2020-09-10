import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SchoolSharedModule } from '../../shared/shared.module';

import { StudentComponent } from './student.component';

import { studentRoute } from './student.route';

@NgModule({
  imports: [SchoolSharedModule, RouterModule.forChild([studentRoute])],
  declarations: [StudentComponent],
})
export class StudentModule {}
