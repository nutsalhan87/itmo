import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MinorService } from '../../minor.service';
import { FormsModule } from '@angular/forms';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { bootstrapPersonBoundingBox, bootstrapEye } from '@ng-icons/bootstrap-icons';
import { AsyncPipe } from '@angular/common';
import { Subscription } from 'rxjs';

class NewPerson {
  id: number;
  relation: 'witness' | 'suspect'; 
  note: string;
  constructor(id: number) {
    this.id = id;
    this.relation = 'witness';
    this.note = '';
  } 
}

export interface Person {
  id: number,
  name: string,
  birthdate: string,
  race: string
}

export interface PersonRelation {
  id: number,
  name: string,
  race: string,
  relation: 'witness' | 'suspect',
  note: string
}

@Component({
  selector: 'app-persons',
  standalone: true,
  imports: [NgIconComponent, FormsModule],
  viewProviders: [provideIcons({ bootstrapPersonBoundingBox, bootstrapEye })],
  templateUrl: './persons.component.html',
  styleUrl: './persons.component.scss'
})
export class PersonsComponent implements OnInit, OnDestroy {
  @Input({ required: true }) crimeId!: number;
  @Input({ required: true }) persons!: Person[];
  temporaryInvalidMark: boolean = false;
  personRelations: PersonRelation[] = [];
  newPerson!: NewPerson;
  subscriptions: Subscription[] = [];
  constructor(private minorService: MinorService) { }
  
  get unrelatedPersons(): Person[] {
    return this.minorService.persons.
      getValue().
      filter(unrelated => {
        return this.personRelations.find(related => related.id == unrelated.id) === undefined
      });
  }
  
  ngOnInit(): void {
    this.newPerson = new NewPerson(this.unrelatedPersons[0]?.id);
    this.subscriptions = [
      this.minorService.getPersonRelations(this.crimeId).subscribe(v => this.personRelations = v)
    ];
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }

  changePersonRelation(person: PersonRelation, newRelation: 'witness' | 'suspect'): void {
    person.relation = newRelation;
    this.minorService.putPersonRelation(this.crimeId, person.id, newRelation, person.note);
  }

  changePersonNote(person: PersonRelation, newNote: string): void {
    this.minorService.putPersonRelation(this.crimeId, person.id, person.relation, newNote);
  }

  createPersonRelation(): void {
    this.minorService.putPersonRelation(this.crimeId, this.newPerson.id, this.newPerson.relation, this.newPerson.note); 
    this.newPerson = new NewPerson(this.unrelatedPersons[0]?.id);
  }
}
