import { Component, OnDestroy } from '@angular/core';
import { PolicemanInfoComponent } from '../policeman-info/policeman-info.component';
import { AuthorizeService } from '../authorize/authorize.service';
import { Router } from '@angular/router';
import { PrecinctComponent } from '../precinct/precinct.component';
import { Policeman } from '../authorize/authorize.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-policeman',
  standalone: true,
  imports: [ PolicemanInfoComponent, PrecinctComponent ],
  templateUrl: './policeman.component.html',
  styleUrl: './policeman.component.scss'
})
export class PolicemanComponent implements OnDestroy {
  self?: Policeman;
  subscription: Subscription;
  constructor(private authService: AuthorizeService, private router: Router) {
    this.subscription = this.authService.self.subscribe(v => this.self = v);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  exit() {
    this.authService.exit();
    this.router.navigateByUrl("/authorize");
  }
}
