import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VehicleFlattened, VehicleFlattenedOmitFrozen, VehicleType, displayVehicleType, typeConstructorsAndValidators as tcav, vehicleFlattenedOmitIdKeys } from '../common';
import { VehiclesService } from '../vehicles.service';
import { first } from 'rxjs';
import { InfoComponent } from "../info/info.component";
import { DatePipe } from '@angular/common';
import { NotificationService } from '../notifications/notification.service';

type FormObject<T> = FormGroup<{
  [Property in keyof T]: FormControl<(T[Property] extends Date ? string : T[Property]) | null> // handtype
}>;

@Component({
  selector: 'app-editor',
  standalone: true,
  imports: [ReactiveFormsModule, InfoComponent, DatePipe],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.scss'
})
export class EditorComponent implements OnInit {
  vehicleOmitIdKeys = vehicleFlattenedOmitIdKeys;
  tcav = tcav;
  id: number | null;
  vehicle: FormObject<VehicleFlattenedOmitFrozen> = new FormGroup({
    name: new FormControl(tcav.name.constructor(), tcav.name.validators),
    x: new FormControl(tcav.x.constructor(), tcav.x.validators),
    y: new FormControl(tcav.y.constructor(), tcav.y.validators),
    enginePower: new FormControl(tcav.enginePower.constructor(), tcav.enginePower.validators),
    numberOfWheels: new FormControl(tcav.numberOfWheels.constructor(), tcav.numberOfWheels.validators),
    distanceTravelled: new FormControl(tcav.distanceTravelled.constructor(), tcav.distanceTravelled.validators),
    type: new FormControl(tcav.type.constructor(), tcav.distanceTravelled.validators),
  });

  constructor(
    route: ActivatedRoute,
    private router: Router,
    private vehiclesService: VehiclesService,
    private notificationService: NotificationService
  ) {
    let id = Number.parseInt(route.snapshot.paramMap.get("id") ?? "");
    this.id = Number.isNaN(id) ? null : id;
  }

  ngOnInit(): void {
    if (this.id == null) {
      return;
    }
    this.vehiclesService.getVehicle(this.id)
      .pipe(first())
      .subscribe({
        next: vehicle => {
          if (vehicle != null) {
            this.vehicle.setValue({
              name: vehicle.name,
              enginePower: vehicle.enginePower,
              numberOfWheels: vehicle.numberOfWheels,
              distanceTravelled: vehicle.distanceTravelled,
              type: vehicle.type,
              x: vehicle.x,
              y: vehicle.y,
            });
          }
        },
        error: this.vehiclesService.handleError,
      });
  }

  getDisplayVehicleType(value: string): string {
    return displayVehicleType[value as VehicleType]; // safety: depends on tcav
  }

  submitVehicle() {
    let vehicle: VehicleFlattenedOmitFrozen = { // safety: function can be called only if form valid, so casting to non-nullable is safe
      name: this.vehicle.controls.name.value as string,
      enginePower: this.vehicle.controls.enginePower.value as number,
      numberOfWheels: this.vehicle.controls.numberOfWheels.value as number,
      distanceTravelled: this.vehicle.controls.distanceTravelled.value as number,
      type: this.vehicle.controls.type.value as VehicleType,
      x: this.vehicle.controls.x.value as number,
      y: this.vehicle.controls.y.value as number,
    };
    if (this.id == null) {
      this.vehiclesService.createVehicle(vehicle)
        .pipe(first())
        .subscribe({
          next: vehicleId => {
            this.notificationService.send("Транспортное средство успешно добавлено");
            this.router.navigateByUrl(`/editor/${vehicleId}`);
          },
          error: this.vehiclesService.handleError,
        });
    } else {
      this.vehiclesService.updateVehicle(this.id, vehicle)
        .pipe(first())
        .subscribe({
          next: _ => this.notificationService.send("Транспортное средство успешно изменено"),
          error: this.vehiclesService.handleError,
        });
    }
  }

  deleteVehicle() {
    this.vehiclesService.deleteVehicle(this.id as number) // safety: can be called only if id is not null
      .pipe(first())
      .subscribe({
        next: _ => {
          this.notificationService.send("Транспортное средство успешно удалено");
          this.router.navigateByUrl("/table/1");
        },
        error: this.vehiclesService.handleError,
      })
  }
}
