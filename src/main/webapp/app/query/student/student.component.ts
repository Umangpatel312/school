import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JhiEventManager } from 'ng-jhipster';
import { combineLatest, Subscription, Observable } from 'rxjs';
import { IUser, User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';
import { HttpResponse, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'jhi-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss'],
})
export class StudentComponent implements OnInit {
  currentAccount: Account | null = null;
  users: User[] | null = null;
  userListSubscription?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;

  roleName: string | any;
  user: IUser[] | null = null;
  constructor(
    private userService: UserService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private eventManager: JhiEventManager,
    private modalService: NgbModal
  ) {}
  // constructor(private userService: UserService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.userListSubscription = this.eventManager.subscribe('userListModification', () => this.loadAll());
    this.activatedRoute.params.subscribe(data => {
      console.log(data);
      this.roleName = data['role'];
      this.handleNavigation();
    });
  }

  transition(): void {
    // const url=`./${role}`
    this.router.navigate([`./${this.roleName}`], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
  }

  private handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      this.page = page !== null ? +page : 1;
      console.log('page:', this.page);
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === 'asc';
      this.loadAll();
    }).subscribe();
  }

  private loadAll(): void {
    this.userService
      .getStudent(this.roleName, {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<User[]>) => this.onSuccess(res.body, res.headers));
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(users: User[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.users = users;
    console.log(users);
  }

  trackIdentity(index: number, item: User): any {
    return item.id;
  }
  // ngOnInit(): void {
  //   // this.route.children.
  //   console.log("student-component")
  //   this.roleName = this.activatedRoute.snapshot.paramMap.get('role');

  //   this.activatedRoute.params.pipe(
  //     switchMap(data => {
  //       console.log(data)
  //       return this.userService.getStudent(data['role'])
  //     })
  //   ).
  //     subscribe
  //     (response => {
  //       console.log(response)
  //       this.user = response
  //     });
  // }
}

// this.route.data.pipe(
//   switchMap(data => {
//     console.log(data)
//     return this.userService.getStudent(data['role'])
//   })
// ).
