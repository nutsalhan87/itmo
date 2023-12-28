import { Component, OnDestroy } from '@angular/core';
import { MinorService } from '../minor.service';
import { Subscription } from 'rxjs';
import { PartnersComponent } from './partners/partners.component';
import { NotesComponent } from './notes/notes.component';
import { Person, PersonsComponent } from './persons/persons.component';

export interface Case {
  id: number,
  type: string,
  district: string
}

@Component({
  selector: 'app-cases',
  standalone: true,
  imports: [PartnersComponent, NotesComponent, PersonsComponent],
  templateUrl: './cases.component.html',
  styleUrl: './cases.component.scss'
})
export class CasesComponent implements OnDestroy {
  cases: Case[] = [];
  persons: Person[] = []
  subscriptions: Subscription[];
  constructor(private minorService: MinorService) {
    this.subscriptions = [
      this.minorService.cases.subscribe(v => this.cases = v),
      this.minorService.persons.subscribe(v => this.persons = v)
    ];
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }
}
