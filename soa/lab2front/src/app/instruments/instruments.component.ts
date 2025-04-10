import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { typeConstructorsAndValidators as tcav} from '../common';
import { VehiclesService } from '../vehicles.service';
import { first } from 'rxjs';
import { NotificationService } from '../notifications/notification.service';
import { InfoComponent } from "../info/info.component";

@Component({
  selector: 'app-instruments',
  standalone: true,
  imports: [ReactiveFormsModule, InfoComponent],
  templateUrl: './instruments.component.html',
  styleUrl: './instruments.component.scss'
})
export class InstrumentsComponent {
  tcav = tcav;
  id = new FormControl<number>(tcav.id.constructor(), tcav.id.validators);
  distanceTravelled = new FormControl<number>(tcav.distanceTravelled.constructor(), tcav.distanceTravelled.validators);

  constructor(private vehiclesService: VehiclesService, private notificationService: NotificationService) {}

  fixDistance() {
    this.vehiclesService.fixDistance(this.id.value as number) // safety: can be called only if valid
      .pipe(first())
      .subscribe({
        next: _ => this.notificationService.send("Пробег успешно сброшен"),
        error: this.vehiclesService.handleError,
      });
  }

  deleteByDistance() {
    this.vehiclesService.deleteByDistanceTravelled(this.distanceTravelled.value as number) // safety: can be called only if valid
      .pipe(first())
      .subscribe({
        next: _ => this.notificationService.send("Транспортное средство успешно удалено"),
        error: this.vehiclesService.handleError,
      });
  }
}
