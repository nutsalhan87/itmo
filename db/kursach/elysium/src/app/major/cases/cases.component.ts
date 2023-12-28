import { Component, OnDestroy } from '@angular/core';
import { MajorService } from '../major.service';
import { Subordinate } from '../subordinates/subordinates.component';
import { Subscription } from 'rxjs';
import { DetectivesComponent } from './detectives/detectives.component';

export interface DetectiveStatus {
  detective: Subordinate,
  status: 'removed' | 'assigned',
  owns: boolean
}

export interface Case {
  id: number,
  type: string,
  district: string,
  state: 'on_work' | 'freeze' | 'close',
  owns: boolean,
  detectives: DetectiveStatus[]
}


@Component({
  selector: 'app-cases',
  standalone: true,
  imports: [DetectivesComponent],
  templateUrl: './cases.component.html'
})
export class CasesComponent implements OnDestroy {
  cases: Case[] = [];
  subordinates: Subordinate[] = [];
  subscriptions: Subscription[];
  constructor(private majorService: MajorService) {
    this.subscriptions = [
      this.majorService.cases.subscribe(v => this.cases = v),
      this.majorService.subordinates.subscribe(v => this.subordinates = v)
    ];
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }

  isUnchangable(cas: Case): boolean {
    return cas.state == 'close' || !cas.owns;
  }

  detectivesNotInCase(cas: Case): Subordinate[] {
    return this.subordinates.filter((subordinate) => {
      return cas.detectives.find((caseDetective) => { return caseDetective.detective.id == subordinate.id; }) === undefined;
    });
  }

  changeCaseState(cas: Case, newState: 'on_work' | 'freeze' | 'close'): void {
    cas.state = newState;
    this.majorService.changeCaseState(cas.id, newState);
  }

  assignToCase(subordinate: Subordinate, cas: Case): void {
    this.majorService.assignToCase(subordinate.id, cas.id);
  }
}
