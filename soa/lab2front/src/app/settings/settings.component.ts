import { Component, OnDestroy } from '@angular/core';
import { VehiclesService } from '../vehicles.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export class SettingsComponent implements OnDestroy {
  masterUrl = new FormControl<string>("", { nonNullable: true });
  slaveUrl = new FormControl<string>("", { nonNullable: true });
  private subscriptions: Subscription[] = [];

  constructor(protected vehiclesService: VehiclesService) {
    this.masterUrl.setValue(vehiclesService.masterUrl.value);
    this.slaveUrl.setValue(vehiclesService.slaveUrl.value);
    this.subscriptions.push(
      this.masterUrl.valueChanges.subscribe({
        next: url => {
          this.vehiclesService.masterUrl.next(url);
        }
      }),
      this.slaveUrl.valueChanges.subscribe({
        next: url => {
          this.vehiclesService.slaveUrl.next(url);
        }
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}
