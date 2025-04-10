import { Component, input, OnInit, output } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { VehicleFlattened, Filter, FilterEqOp, FilterCmpOp, FilterInOp, typeConstructorsAndValidators as tcav, displayVehicleType, stringFromDate, VehicleType } from '../../common';
import { InfoComponent } from '../../info/info.component';

const supportedFilters: { [Property in keyof VehicleFlattened]: Filter<VehicleFlattened[Property]>["op"][] } = { // handtype
  id: Object.values<FilterEqOp | FilterCmpOp>(FilterEqOp).concat(Object.values(FilterCmpOp)),
  name: Object.values<FilterEqOp | FilterInOp>(FilterEqOp).concat(Object.values(FilterInOp)),
  creationDate: Object.values<FilterEqOp | FilterCmpOp>(FilterEqOp).concat(Object.values(FilterCmpOp)),
  enginePower: Object.values<FilterEqOp | FilterCmpOp>(FilterEqOp).concat(Object.values(FilterCmpOp)),
  numberOfWheels: Object.values<FilterEqOp | FilterCmpOp>(FilterEqOp).concat(Object.values(FilterCmpOp)),
  distanceTravelled: Object.values<FilterEqOp | FilterCmpOp>(FilterEqOp).concat(Object.values(FilterCmpOp)),
  type: Object.values<FilterEqOp>(FilterEqOp),
  x: Object.values<FilterEqOp | FilterCmpOp>(FilterEqOp).concat(Object.values(FilterCmpOp)),
  y: Object.values<FilterEqOp | FilterCmpOp>(FilterEqOp).concat(Object.values(FilterCmpOp)),
};

type FilterForm<T extends keyof VehicleFlattened> = {
  op: Filter<VehicleFlattened[T]>["op"],
  form: FormControl<(VehicleFlattened[T] extends Date ? string : VehicleFlattened[T]) | null> // handtype 
};

@Component({
  selector: 'app-filters',
  standalone: true,
  imports: [ReactiveFormsModule, InfoComponent],
  templateUrl: './filters.component.html',
  styleUrl: './filters.component.scss'
})
export class FiltersComponent<T extends keyof VehicleFlattened> implements OnInit {
  tcav = tcav;
  filtersOutput = output<Filter<VehicleFlattened[T]>[]>();
  filtersInput = input.required<Filter<VehicleFlattened[T]>[]>();
  filters: FilterForm<T>[] = [];
  column = input.required<T>();

  get inputType() {
    return tcav[this.column()].inputType;
  }

  get helperMessage() {
    return tcav[this.column()].helperMessage;
  }

  ngOnInit(): void {
    this.clearFilters();
  }

  isValid(): boolean {
    return this.filters.every(filter => filter.form.valid);
  }

  getDisplayVehicleType(value: string): string {
    return displayVehicleType[value as VehicleType]; // safety: depends on tcav
  }

  getSupportedFilters(): Filter<VehicleFlattened[T]>["op"][] {
    return supportedFilters[this.column()];
  }

  addFilter(filterOp: Filter<VehicleFlattened[T]>["op"]) {
    let filter: FilterForm<T> = {
      op: filterOp,
      form: new FormControl(tcav[this.column()].constructor(),
        tcav[this.column()].validators),
    };
    this.filters.push(filter);
  }

  deleteFilter(idx: number) {
    this.filters.splice(idx, 1);
  }

  saveFilters() {
    let filters: Filter<VehicleFlattened[T]>[] = this.filters
      .map(filter => {
        return {
          op: filter.op,
          value: filter.form.value as VehicleFlattened[T], // safety: method can be called only when all forms are valid
        };
      });
    this.filtersOutput.emit(filters);
  }

  clearFilters<T extends keyof VehicleFlattened>() {
    this.filters = this.filtersInput()
      .map(filter => {
        let value: FilterForm<T>["form"]["value"] = filter.value instanceof Date
          ? stringFromDate(filter.value)
          : filter.value as any; // safety: it's ok
        return {
          op: filter.op,
          form: new FormControl(value, tcav[this.column()].validators)
        };
      }) as any; // safety: it's should be ok
  }
}
