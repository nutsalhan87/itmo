import { Component } from '@angular/core';
import { PolicemanComponent } from '../policeman/policeman.component';
import { CasesComponent } from './cases/cases.component';

@Component({
  selector: 'app-minor',
  standalone: true,
  imports: [PolicemanComponent, CasesComponent],
  templateUrl: './minor.component.html'
})
export class MinorComponent {
  constructor() { }
}
