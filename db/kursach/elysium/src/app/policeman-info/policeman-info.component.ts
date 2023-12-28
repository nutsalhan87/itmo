import { Component, OnDestroy } from '@angular/core';
import { AuthorizeService } from '../authorize/authorize.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-policeman-info',
  standalone: true,
  imports: [],
  templateUrl: './policeman-info.component.html',
  styleUrl: './policeman-info.component.scss'
})
export class PolicemanInfoComponent implements OnDestroy {
  name!: string;
  rank!: string;
  series!: string;
  dateOfIssue!: string;
  subscription: Subscription;
  constructor(private authService: AuthorizeService) {
    this.subscription = this.authService.self.subscribe(v => {
      if (v) {
        this.name = v.name;
        this.rank = v.rank;
        this.series = v.series;
        this.dateOfIssue = v.dateOfIssue;
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
