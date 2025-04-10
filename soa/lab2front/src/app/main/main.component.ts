import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { VehiclesService } from '../vehicles.service';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { SortingComponent } from './sorting/sorting.component';
import { FiltersComponent } from './filters/filters.component';
import { Filter, FilterMap, Sort, VehicleFlattened, stringFromDate, typeConstructorsAndValidators as tcav, vehicleFlattenedKeys, displayVehicleType, integerValidator } from '../common';

const pagesAmplitude = 2;

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [ReactiveFormsModule, SortingComponent, FiltersComponent],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent implements OnDestroy {
  pagesAmplitude = pagesAmplitude;
  tcav = tcav;
  vehicleFlattenedKeys = vehicleFlattenedKeys;
  displayVehicleType = displayVehicleType;
  stringFromDate = stringFromDate;

  vehicles: VehicleFlattened[] = [];

  filters: FilterMap = {
    id: [],
    name: [],
    creationDate: [],
    enginePower: [],
    numberOfWheels: [],
    distanceTravelled: [],
    type: [],
    x: [],
    y: [],
  };

  sorting: Sort[] = [];

  pageInfo = {
    total: 0,
    current: 1,
    elements: new FormControl<number>(20, [Validators.min(1), integerValidator]),
  };

  subscriptions: Subscription[];

  constructor(
    route: ActivatedRoute,
    private router: Router,
    private vehiclesService: VehiclesService,
  ) {
    this.subscriptions = new Array(
      vehiclesService.vehicles.subscribe({
        next: vehicles => this.vehicles = vehicles,
      }),
      vehiclesService.totalPages.subscribe({
        next: totalPages => this.pageInfo.total = totalPages,
      }),
      router.events.subscribe({
        next: event => {
          if (event instanceof NavigationEnd) {
            this.pullVehicles();
          }
        }
      }),
    );

    let page = route.snapshot.paramMap.get("page");
    if (page) {
      let parsed = Number.parseInt(page);
      if (Number.isNaN(parsed) || parsed < 1) {
        router.navigateByUrl("/table/1");
      }
      this.pageInfo.current = parsed;
    }
  }

  getFilters<T extends keyof VehicleFlattened>(column: T): Filter<VehicleFlattened[T]>[] {
    return this.filters[column];
  }

  setFilters<T extends keyof VehicleFlattened>(column: T, filters: Filter<VehicleFlattened[T]>[]) {
    this.filters[column] = filters as any; // safety: already checked type
  }

  pullVehicles() {
    this.vehiclesService.pullVehicles(
      this.pageInfo.current,
      this.pageInfo.elements.value as number, // safety: can be called only if pages is valid
      this.sorting,
      this.filters);
  }

  selectPage(page: number) {
    this.router.navigateByUrl(`/table/${page}`);
    this.pageInfo.current = page;
    this.pullVehicles();
  }

  pagesAround(): number[] {
    let start = Math.max(1, this.pageInfo.current - pagesAmplitude);
    let end = Math.min(this.pageInfo.total, this.pageInfo.current + pagesAmplitude);
    return Array.from<number, number>(
      { length: end - start + 1 },
      (_, i) => start + i
    );
  }

  openEditor(idx: number) {
    this.router.navigateByUrl(`/editor/${idx}`);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
