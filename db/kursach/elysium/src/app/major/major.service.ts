import { Injectable } from '@angular/core';
import { Subordinate } from './subordinates/subordinates.component';
import { Crime } from './crimes/crimes.component';
import { Case } from './cases/cases.component';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

interface SubordinateResponse {
  id: number,
  name: string,
  series: string,
  seriesDateOfIssue: string,
  owns: boolean
}

interface CaseResponse {
  crimeCases: {
    id: number,
    type: string,
    district: string,
    state: 'on_work' | 'freeze' | 'close',
    owns: boolean,
    slavesOnCase: {
      id: number,
      status: 'assigned' | 'removed'
    }[]
  }[],
  uniquePolicemen: SubordinateResponse[]
}

@Injectable({
  providedIn: 'root'
})
export class MajorService {
  subordinates: BehaviorSubject<Subordinate[]> = new BehaviorSubject(new Array<Subordinate>());
  crimes: BehaviorSubject<Crime[]> = new BehaviorSubject(new Array<Crime>());
  cases: BehaviorSubject<Case[]> = new BehaviorSubject(new Array<Case>());
  constructor(private http: HttpClient) {
    this.updateSubordinates();
    this.updateCrimes();
    this.updateCases();
  }

  updateSubordinates(): void {
    this.http.get<SubordinateResponse[]>('/api/v1/auth/major/slaves/my').subscribe({
      next: res => {
        this.subordinates.next(res.map(v => ({
          id: v.id,
          name: v.name,
          series: v.name,
          dateOfIssue: v.seriesDateOfIssue,
        })));
      },
      error: e => {
        console.log(e);
      }
    });
  }

  updateCrimes(): void {
    this.http.get<Crime[]>('/api/v1/auth/major/crime/in-my-precinct-districts/list').subscribe({
      next: res => {
        this.crimes.next(res);
      },
      error: e => {
        console.log(e);
      }
    })
  }

  updateCases(): void {
    this.http.get<CaseResponse>('/api/v1/auth/major/crime-case/my').subscribe({
      next: res => {
        this.cases.next(res.crimeCases.map(v => ({
          id: v.id,
          type: v.type,
          district: v.district,
          state: v.state,
          owns: v.owns,
          detectives: v.slavesOnCase.map(d => {
            let detective = res.uniquePolicemen.find(b => b.id == d.id) ?? { id: d.id, name: '', series: '', seriesDateOfIssue: '', owns: false };
            return {
              detective: {
                id: detective.id,
                name: detective.name,
                series: detective.series,
                dateOfIssue: detective.seriesDateOfIssue,
              },
              status: d.status,
              owns: detective.owns
            };
          })
        })));
      },
      error: e => {
        console.log(e);
      }
    });
  }

  changeDetectiveStatus(detectiveId: number, crimeId: number, status: 'assigned' | 'removed'): void {
    this.http.put('/api/v1/auth/major/policeman-case/put', {
      policemanId: detectiveId,
      crimeId,
      onCaseStatus: status
    },
    { observe: 'response' }).subscribe({
      next: v => {
        if (v.status == 201) {
          this.updateCases();
        }
      },
      error: e => {
        console.log(e);
      }
    })
  }

  assignToCase(detectiveId: number, crimeId: number): void {
    this.changeDetectiveStatus(detectiveId, crimeId, 'assigned');
  }

  changeCaseState(crimeId: number, state: 'on_work' | 'freeze' | 'close'): void {
    this.http.patch(`/api/v1/auth/major/crime-case/${crimeId}/update/state/${state}`, {}).subscribe({
      error: e => {
        console.log(e);
      }
    });
  }

  openCase(crimeId: number): void {
    this.http.post('/api/v1/auth/major/crime-case/create', { crimeId }).subscribe({
      next: _ => {
        this.updateCrimes();
        this.updateCases();
      },
      error: e => {
        console.log(e);
      }
    });
  }
}
