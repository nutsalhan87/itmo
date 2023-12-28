import { Component, OnDestroy } from '@angular/core';
import { MajorService } from '../major.service';
import { Subscription } from 'rxjs';

export interface Crime {
  id: number,
  type: string,
  district: string,
  caseOpened: boolean
}

@Component({
  selector: 'app-crimes',
  standalone: true,
  imports: [],
  templateUrl: './crimes.component.html'
})
export class CrimesComponent implements OnDestroy {
  crimes: Crime[] = [];
  subscriptions: Subscription[];
  constructor(private majorService: MajorService) {
    this.subscriptions = [
      this.majorService.crimes.subscribe(v => this.crimes = v)
    ]
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }

  openCase(crime: Crime): void {
    this.majorService.openCase(crime.id);
  }
}
