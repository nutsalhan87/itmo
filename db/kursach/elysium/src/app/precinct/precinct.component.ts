import { Component, OnDestroy } from '@angular/core';
import { AuthorizeService } from '../authorize/authorize.service';
import { Subscription } from 'rxjs';

export interface Precinct {
  number: number,
  districts: {id: number, name: string}[]
}

@Component({
  selector: 'app-precinct',
  standalone: true,
  imports: [],
  templateUrl: './precinct.component.html',
  styleUrl: './precinct.component.scss'
})
export class PrecinctComponent implements OnDestroy {
  precinct!: Precinct;
  subscription: Subscription;
  constructor(private authService: AuthorizeService) {
    this.subscription = this.authService.precinct.subscribe(v => {
      if (v) {
        this.precinct = v;
      }
    });
  }
  
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
