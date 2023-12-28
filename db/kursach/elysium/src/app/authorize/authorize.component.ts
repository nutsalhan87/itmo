import { Component, OnDestroy } from '@angular/core';
import { AuthorizeService } from './authorize.service';
import { Router } from '@angular/router';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { emptyValidator } from '../../util/util';
import { Subscription } from 'rxjs';

export interface Policeman {
  name: string;
  rank: 'minor' | 'major';
  series: string;
  dateOfIssue: string;
}

@Component({
  selector: 'app-authorize',
  standalone: true,
  imports: [ ReactiveFormsModule ],
  templateUrl: './authorize.component.html',
  styleUrl: './authorize.component.scss'
})
export class AuthorizeComponent implements OnDestroy {
  login: FormControl = new FormControl('', emptyValidator);
  password: FormControl = new FormControl('', emptyValidator);
  subscription: Subscription;
  constructor(private authService: AuthorizeService, private router: Router) {
    this.subscription = this.authService.self.subscribe(v => {
      if (v) {
        if (v.rank == 'major') {
          this.router.navigateByUrl('/major');
        } else {
          this.router.navigateByUrl('/minor');
        }
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  submit() {
    this.authService.login(this.login.value, this.password.value);
  }
}
