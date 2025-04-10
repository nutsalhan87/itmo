import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import {
  Vehicle, Sort, FilterMap, VehicleFlattened,
  vehicleFlattenedKeys,
  filterOpOperators, dateFromString,
  VehicleFlattenedOmitFrozen, typeConstructorsAndValidators as tcav
} from './common';
import { NotificationService } from './notifications/notification.service';

type VehicleServerTyped = {
  [Property in keyof Vehicle]: Vehicle[Property] extends Date ? string : Vehicle[Property]
};

interface GetVehiclesParams {
  page: number,
  size: number,
  sort?: string,
  filter?: string,
}

type CUVehicleRequest = Omit<Vehicle, "id" | "creationDate">;

interface GetVehiclesResponse {
  totalPages: number,
  vehicles: VehicleServerTyped[],
}

interface CreateVehicleResponse {
  id: number,
  creationDate: string,
}

type UpdateVehicleResponse = Omit<VehicleServerTyped, "id">;

type Error = {
  timestamp: string,
  error: string,
  path: string,
  status: number,
} & (
    {
      status: 400
    } | {
      status: 404,
      details: string
    } | {
      status: 422,
      details: {
        field: keyof VehicleFlattened,
        value: string
      }[]
    }
  );

@Injectable({
  providedIn: 'root'
})
export class VehiclesService {
  masterUrl = new BehaviorSubject<string>("https://localhost:8443/jaxbu-1.0/api/v1");
  slaveUrl = new BehaviorSubject<string>("https://localhost:8444/godbless/api/v1/shop");
  totalPages = new BehaviorSubject<number>(0);
  vehicles = new BehaviorSubject<VehicleFlattened[]>([]);
  constructor(private httpClient: HttpClient, private notificationsService: NotificationService) { }

  public handleError(error: HttpErrorResponse) {
    if (error.status != 400 && error.status != 404 && error.status != 422 && error.status < 500) {
      console.log(error);
      return;
    }
    let err = error.error as Error;
    if (err.status == 400) {
      this.notificationsService.send(err.error);
    } else if (err.status == 404) {
      this.notificationsService.send(err.details);
    } else if (err.status == 422) {
      let validationErrorsFormatted = err.details.map(detail => {
        return `Ошибка в поле "${tcav[detail.field]}": ${detail.value}`
      }).join('\n');
      this.notificationsService.send("Ошибки при валидации данных", validationErrorsFormatted);
    } else {
      this.notificationsService.send("Внутренняя ошибка сервера", "Свяжитесь с администратором: +7 (XXX) XXX-XX-XX");
    }
  }

  pullVehicles(page: number, size: number, sorting: Sort[], filters: FilterMap) {
    let sort: string = sorting.map(s => s.isAsc ? s.key : `-${s.key}`).join(",");
    let filter: string = vehicleFlattenedKeys
      .map(key => {
        return filters[key]
          .map(f => `${key}${filterOpOperators[f.op]}${f.value}`)
          .join(",");
      })
      .filter(f => f.length != 0)
      .join(",");
    let params: GetVehiclesParams = { page, size };
    if (sort.length != 0) {
      params["sort"] = sort;
    }
    if (filter.length != 0) {
      params["filter"] = filter;
    }
    this.httpClient.get<GetVehiclesResponse>(this.masterUrl.value + "/vehicles", {
      params: params as any, // safety: it's ok
    }).subscribe({
      next: answer => {
        this.vehicles.next(answer.vehicles.map(vehicle => ({
          id: vehicle.id,
          name: vehicle.name,
          creationDate: dateFromString(vehicle.creationDate) as Date, // safety: we wait correct answer from server
          enginePower: vehicle.enginePower,
          numberOfWheels: vehicle.numberOfWheels,
          distanceTravelled: vehicle.distanceTravelled,
          type: vehicle.type,
          x: vehicle.coordinates.x,
          y: vehicle.coordinates.y
        })));
        this.totalPages.next(answer.totalPages);
      },
      error: this.handleError,
    });
  }

  getVehicle(id: number): Observable<VehicleFlattened | null> {
    return this.httpClient.get<GetVehiclesResponse>(
      this.masterUrl.value + "/vehicles",
      {
        params: {
          page: 1, size: 1, filter: `id=${id}`,
        },
      })
      .pipe(map(answer => {
        if (answer.vehicles.length == 0) {
          return null;
        } else {
          return {
            id: answer.vehicles[0].id,
            name: answer.vehicles[0].name,
            creationDate: dateFromString(answer.vehicles[0].creationDate) as Date, // safety: we wait correct answer from server
            enginePower: answer.vehicles[0].enginePower,
            numberOfWheels: answer.vehicles[0].numberOfWheels,
            distanceTravelled: answer.vehicles[0].distanceTravelled,
            type: answer.vehicles[0].type,
            x: answer.vehicles[0].coordinates.x,
            y: answer.vehicles[0].coordinates.y
          };
        }
      }));
  }

  createVehicle(vehicle: VehicleFlattenedOmitFrozen): Observable<number> {
    let requestBody: CUVehicleRequest = {
      coordinates: {
        x: vehicle.x,
        y: vehicle.y,
      },
      name: vehicle.name,
      enginePower: vehicle.enginePower,
      numberOfWheels: vehicle.numberOfWheels,
      distanceTravelled: vehicle.distanceTravelled,
      type: vehicle.type,
    };
    return this.httpClient.post<CreateVehicleResponse>(this.masterUrl.value + "/vehicles", requestBody)
      .pipe(map(vehicle => vehicle.id));
  }

  updateVehicle(id: number, vehicle: VehicleFlattenedOmitFrozen): Observable<VehicleFlattenedOmitFrozen> {
    let requestBody: CUVehicleRequest = {
      coordinates: {
        x: vehicle.x,
        y: vehicle.y,
      },
      name: vehicle.name,
      enginePower: vehicle.enginePower,
      numberOfWheels: vehicle.numberOfWheels,
      distanceTravelled: vehicle.distanceTravelled,
      type: vehicle.type,
    };
    return this.httpClient.patch<UpdateVehicleResponse>(this.masterUrl.value + `/vehicles/${id}`, requestBody)
      .pipe(map(answerVehicle => ({
        name: answerVehicle.name,
        creationDate: answerVehicle.creationDate,
        enginePower: answerVehicle.enginePower,
        numberOfWheels: answerVehicle.numberOfWheels,
        distanceTravelled: answerVehicle.distanceTravelled,
        type: answerVehicle.type,
        x: answerVehicle.coordinates.x,
        y: answerVehicle.coordinates.y,
      })));
  }

  deleteVehicle(id: number): Observable<null> {
    return this.httpClient.delete<null>(this.masterUrl.value + `/vehicles/${id}`);
  }

  fixDistance(id: number): Observable<null> {
    return this.httpClient.patch<null>(this.slaveUrl.value + `/vehicles/fix-distance/${id}`, null);
  }

  deleteByDistanceTravelled(distance: number): Observable<null> {
    return this.httpClient.delete<null>(this.slaveUrl.value + `/vehicles/any-with-distance/${distance}`);
  }
}
