import { Injectable } from '@angular/core';
import { BehaviorSubject, firstValueFrom } from 'rxjs';
import { Case } from './cases/cases.component';
import { Partner } from './cases/partners/partners.component';
import { Note } from './cases/notes/notes.component';
import { Person, PersonRelation } from './cases/persons/persons.component';
import { HttpClient } from '@angular/common/http';

interface PartnerResponse {
  id: number,
  name: string,
  series: string,
  seriesDateOfIssue: string,
  onCaseStatus: 'assigned' | 'removed'
}

@Injectable({
  providedIn: 'root'
})
export class MinorService {
  persons: BehaviorSubject<Person[]> = new BehaviorSubject(new Array());
  cases: BehaviorSubject<Case[]> = new BehaviorSubject(new Array());
  private partners: Map<number, BehaviorSubject<Partner[]>> = new Map();
  private notes: Map<number, BehaviorSubject<Note[]>> = new Map();
  private personRelations: Map<number, BehaviorSubject<PersonRelation[]>> = new Map();
  constructor(private http: HttpClient) {
    this.updatePersons();
    this.updateCases();
  }

  updatePersons(): void {
    this.http.get<Person[]>('/api/v1/auth/minor/person/list').subscribe({
      next: res => {
        this.persons.next(res);
      },
      error: e => {
        console.log(e);
      }
    });
  }

  updateCases(): void {
    this.http.get<Case[]>('/api/v1/auth/minor/crime-case/available').subscribe({
      next: res => {
        this.cases.next(res);
      },
      error: e => {
        console.log(e);
      }
    });
  }

  getPartners(crimeId: number): BehaviorSubject<Partner[]> {
    let partners = this.partners.get(crimeId);
    if (partners) {
      return partners;
    } else {
      let partners2 = new BehaviorSubject(new Array<Partner>());
      this.partners.set(crimeId, partners2);
      this.updatePartners(crimeId);
      return partners2;
    }
  }

  updatePartners(crimeId: number): void {
    let partners = this.partners.get(crimeId);
    this.http.get<PartnerResponse[]>(`/api/v1/auth/minor/policeman/partners-in-case/${crimeId}`).subscribe({
      next: res => {
        partners?.next(res.map(v => ({
          id: v.id,
          name: v.name,
          series: v.series,
          dateOfIssue: v.seriesDateOfIssue,
          status: v.onCaseStatus
        })));
      },
      error: e => {
        console.log(e);
      }
    });
  }

  getNotes(crimeId: number): BehaviorSubject<Note[]> {
    let notes = this.notes.get(crimeId);
    if (notes) {
      return notes;
    } else {
      let notes2 = new BehaviorSubject(new Array<Note>());
      this.notes.set(crimeId, notes2);
      this.updateNotes(crimeId);
      return notes2;
    }
  }
  
  updateNotes(crimeId: number): void {
    let notes = this.notes.get(crimeId);
    this.http.get<Note[]>(`/api/v1/auth/minor/note/in-case/${crimeId}`).subscribe({
      next: res => {
        notes?.next(res);
      },
      error: e => {
        console.log(e);
      }
    });
  }

  getPersonRelations(crimeId: number): BehaviorSubject<PersonRelation[]> {
    let personRelations = this.personRelations.get(crimeId);
    if (personRelations) {
      return personRelations;
    } else {
      let personRelations2 = new BehaviorSubject(new Array<PersonRelation>());
      this.personRelations.set(crimeId, personRelations2);
      this.updatePersonRelations(crimeId);
      return personRelations2;
    }
  }
  
  updatePersonRelations(crimeId: number): void {
    let personRelations = this.personRelations.get(crimeId);
    this.http.get<PersonRelation[]>(`/api/v1/auth/minor/person-relevant-to-case/in-case/${crimeId}`).subscribe({
      next: res => {
        personRelations?.next(res);
      },
      error: e => {
        console.log(e);
      }
    });
  }

  createNote(crimeId: number, note: string): void {
    this.http.post('/api/v1/auth/minor/note/create', { crimeId, text: note }).subscribe({
      next: _ => {
        this.updateNotes(crimeId);
      },
      error: e => {
        console.log(e);
      }
    });
  }

  putPersonRelation(crimeId: number, personId: number, relation: 'suspect' | 'witness', note: string): void {
    this.http.put('/api/v1/auth/minor/person-relevant-to-case/put', { crimeId, personId, relation, note }, { observe: 'response'}).subscribe({
      next: v => {
        if (v.status == 201) {
          this.updatePersonRelations(crimeId);
        }
      },
      error: e => {
        console.log(e);
      }
    });
  }
}
