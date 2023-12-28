import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Policeman } from './authorize.component';
import { Precinct } from '../precinct/precinct.component';
import { BehaviorSubject, map, take } from 'rxjs';

const JSESSIONID = 'JSESSIONID';

interface PolicemanResponse {
  name: string,
  rank: 'minor' | 'major',
  series: string,
  seriesDateOfIssue: string
}

export const minorGuard: CanActivateFn = (route, state) => {
  const http = inject(HttpClient);
  const router = inject(Router);
  return http.get<PolicemanResponse>('/api/v1/auth/policeman/self').pipe(map((v, _) => {
    console.log(v);
    if (v.rank == 'minor') {
      return true;
    } else {
      return router.parseUrl('/authorize');
    }
  }))
    .pipe(take(1));
};

export const majorGuard: CanActivateFn = (route, state) => {
  const http = inject(HttpClient);
  const router = inject(Router);
  return http.get<PolicemanResponse>('/api/v1/auth/policeman/self').pipe(map((v, _) => {
    if (v.rank == 'major') {
      return true;
    } else {
      return router.parseUrl('/authorize');
    }
  }))
    .pipe(take(1));
};

@Injectable({
  providedIn: 'root'
})
export class AuthorizeService {
  self: BehaviorSubject<Policeman | undefined> = new BehaviorSubject<Policeman | undefined>(undefined);
  precinct: BehaviorSubject<Precinct | undefined> = new BehaviorSubject<Precinct | undefined>(undefined);
  constructor(private http: HttpClient) { }

  updateSelf(): void {
    this.http.get<PolicemanResponse>('/api/v1/auth/policeman/self').subscribe({
      next: res => {
        this.self.next({
          name: res.name,
          rank: res.rank,
          series: res.series,
          dateOfIssue: res.seriesDateOfIssue
        });
      },
      error: e => {
        console.log(e);
      }
    });
  }

  updatePrecinct(): void {
    this.http.get<Precinct>('/api/v1/auth/precinct/my').subscribe({
      next: res => {
        this.precinct.next(res);
      },
      error: e => {
        console.log(e);
      }
    });
  }

  login(series: string, password: string): void {
    this.http.post('/api/v1/public/login', { series, password }).subscribe({
      next: _ => {
        this.updateSelf();
        this.updatePrecinct();
      },
      error: e => {
        console.log(e);
      }
    });
  }

  exit(): void {
    this.self.next(undefined);
  }
}
