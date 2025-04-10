import { Component, input, output } from '@angular/core';
import { Sort, VehicleFlattened, typeConstructorsAndValidators as tcav, vehicleFlattenedKeys } from '../../common';

@Component({
  selector: 'app-sorting',
  standalone: true,
  imports: [],
  templateUrl: './sorting.component.html',
  styleUrl: './sorting.component.scss'
})
export class SortingComponent {
  tcav = tcav;
  sortingOutput = output<Sort[]>();
  sortingInput = input.required<Sort[]>();

  deleteByIndex(idx: number) {
    let sorting = this.sortingInput();
    sorting.splice(idx, 1);
    this.sortingOutput.emit(sorting);
  }

  add(key: keyof VehicleFlattened) {
    let sorting = this.sortingInput();
    let sort: Sort = {
      key,
      isAsc: true
    };
    sorting.push(sort);
    this.sortingOutput.emit(sorting);
  }

  changeDirection(idx: number, sort: Sort) {
    let sorting = this.sortingInput();
    sorting[idx].isAsc = !sorting[idx].isAsc;
    this.sortingOutput.emit(sorting);
  }

  up(idx: number) {
    if (idx == 0) {
      return;
    }
    let sorting = this.sortingInput();
    [sorting[idx], sorting[idx - 1]] = [sorting[idx - 1], sorting[idx]];
    this.sortingOutput.emit(sorting);
  }

  down(idx: number) {
    let sorting = this.sortingInput();
    if (idx + 1 == sorting.length) {
      return;
    }
    [sorting[idx], sorting[idx + 1]] = [sorting[idx + 1], sorting[idx]];
    this.sortingOutput.emit(sorting);
  }

  getColumnHeader(sort: Sort): string {
    return tcav[sort.key].header;
  }

  get columnHeaders(): (keyof VehicleFlattened)[] {
    return vehicleFlattenedKeys.filter(key => {
      return this.sortingInput().find(sort => sort.key == key) == undefined;
    });
  }
}
