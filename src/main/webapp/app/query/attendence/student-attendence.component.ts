import { Component, OnInit, OnDestroy } from '@angular/core';
import { GradeStudentService } from 'app/entities/grade-student/grade-student.service';
import { IGradeStudent } from 'app/shared/model/grade-student.model';
import { Subscription, Subject } from 'rxjs';
import { IAttendenceStudent } from 'app/shared/model/attendence-student.model';
import { AttendenceStudentService } from 'app/entities/attendence-student/attendence-student.service';
import { debounceTime } from 'rxjs/operators';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-app-student-attendence',
  templateUrl: './student-attendence.component.html',
})
export class StudentAttendenceComponent implements OnInit, OnDestroy {
  private _success = new Subject<string>();
  successMessage = '';
  // flagSuccess = '';
  gradeStudent: IGradeStudent[] | null = null;
  gradeStudentSubscription?: Subscription;
  attendenceStudent: IAttendenceStudent[] = [];
  constructor(
    private gradeStudentService: GradeStudentService,
    private attendenceStudentService: AttendenceStudentService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._success.subscribe(message => {
      this.successMessage = message;
      console.log(message);
    });
    this._success.pipe(debounceTime(5000)).subscribe(() => (this.successMessage = ''));
    this.gradeStudentSubscription = this.gradeStudentService.getStudentByTeacher().subscribe(response => {
      console.log(response);
      this.gradeStudent = response.body;
      this.addDateToIAttendenceStudent(response.body);
      console.log(this.gradeStudent);
    });
  }
  addDateToIAttendenceStudent(response: IGradeStudent[] | null): void {
    if (response != null) {
      for (const i of response) {
        console.log(i.userLogin);
        this.attendenceStudent?.push({
          userId: i.userId,
          userLogin: i.userLogin,
          marked: 'P',
        });
      }
    }
    console.log('data', this.attendenceStudent);
  }
  onClickRadio(index: number, input: string): void {
    this.attendenceStudent[index].marked = input;
    console.log(this.attendenceStudent);
  }
  ngOnDestroy(): void {
    this.gradeStudentSubscription?.unsubscribe();
  }
  onSubmit(): void {
    console.log(this.attendenceStudent);
    this.attendenceStudentService.takeAttendence(this.attendenceStudent).subscribe(
      response => {
        console.log(response);
        this.goToAttendenceView();
      },
      err => {
        console.log(err);
        this.changeSuccessMessage(err);
      }
    );
  }
  goToAttendenceView(): void {
    this.router.navigate(['./view'], { relativeTo: this.route });
  }
  public changeSuccessMessage(error: any): void {
    this._success.next(error.error.title);
  }
}
// export class StudentAttendenceComponent implements OnInit, OnDestroy {
//   currentAccount: Account | null = null;
//   gradeStudent: IGradeStudent[] | null = null;
//   gradeStudentSubscription?: Subscription
//   attendenceForm!: FormGroup
//   constructor(
//     private gradeStudentService: GradeStudentService, private fb: FormBuilder
//   ) { }

//   get student(): FormArray {
//     return this.attendenceForm?.get('student') as FormArray
//   }
//   addStudent = (response: IGradeStudent[] | null) => {
//     if (response != null) {
//       for (const i of response) {
//         this.student.push(new FormGroup({
//           id: new FormControl(i.userId),
//           login: new FormControl(i.userLogin),
//           option: new FormControl('p')
//         }))
//       }
//     }
//   }
//   ngOnInit(): void {
//     this.attendenceForm = this.fb.group({
//       student: this.fb.array([])
//     })
//     this.gradeStudentSubscription = this.gradeStudentService.getStudentByTeacher().subscribe(
//       (response) => {
//         console.log(response);
//         this.gradeStudent = response.body;
//         this.student.clear()
//         this.addStudent(response.body)
//         console.log(this.gradeStudent);
//       }
//     );
//   }

//   onSubmit(): void {
//     console.log(this.student.value)
//   }
//   ngOnDestroy(): void {
//     this.gradeStudentSubscription?.unsubscribe()
//   }
// }
