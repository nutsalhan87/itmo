import { Component, OnDestroy } from '@angular/core';
import { MajorService } from '../major.service';
import { Subscription } from 'rxjs';

export interface Subordinate {
  id: number,
  name: string,
  series: string,
  dateOfIssue: string
}

@Component({
  selector: 'app-subordinates',
  standalone: true,
  imports: [],
  templateUrl: './subordinates.component.html'
})
export class SubordinatesComponent implements OnDestroy {
  subordinates: Subordinate[] = [];
  subscriptions: Subscription[];
  constructor(private majorService: MajorService) {
    this.subscriptions = [
      this.majorService.subordinates.subscribe(v => this.subordinates = v)
    ];
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }
}
