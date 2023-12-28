import { Component } from '@angular/core';
import { PolicemanComponent } from '../policeman/policeman.component';
import { SubordinatesComponent } from './subordinates/subordinates.component';
import { CrimesComponent } from './crimes/crimes.component';
import { CasesComponent } from './cases/cases.component';

@Component({
  selector: 'app-major',
  standalone: true,
  imports: [ PolicemanComponent, SubordinatesComponent, CrimesComponent, CasesComponent ],
  templateUrl: './major.component.html'
})
export class MajorComponent {
  constructor() { }
}
