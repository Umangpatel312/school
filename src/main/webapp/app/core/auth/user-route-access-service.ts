import { Injectable, isDevMode } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { StateStorageService } from './state-storage.service';

@Injectable({ providedIn: 'root' })
export class UserRouteAccessService implements CanActivate {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private loginModalService: LoginModalService,
    private accountService: AccountService,
    private stateStorageService: StateStorageService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    const authorities: string[] = route.data['authorities'];
    // We need to call the checkLogin / and so the accountService.identity() function, to ensure,
    // that the client has a principal too, if they already logged in by the server.
    // This could happen on a page refresh.

    this.router.events.subscribe(data => console.log(data));
    console.log(route.data);
    this.route.data.subscribe(d => console.log(d));
    return this.checkLogin(authorities, state.url);
  }

  checkLogin(authorities: string[], url: string): Observable<boolean> {
    return this.accountService.identity().pipe(
      map(account => {
        /* eslint-disable no-console */
        console.log(authorities, account);
        /* eslint-enable no-console */
        if (!authorities || authorities.length === 0) {
          return true;
        }

        if (account) {
          const hasAnyAuthority = this.accountService.hasAnyAuthority(authorities);
          if (hasAnyAuthority) {
            return true;
          }
          /* eslint-disable no-console */
          console.log(isDevMode());
          /* eslint-enable no-console */
          if (isDevMode()) {
            console.error('User has not any of required authorities: ', authorities);
          }
          this.router.navigate(['accessdenied']);
          return false;
        }

        this.stateStorageService.storeUrl(url);
        this.router.navigate(['']);
        this.loginModalService.open();
        return false;
      })
    );
  }
}
