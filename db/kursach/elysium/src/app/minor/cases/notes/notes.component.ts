import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MinorService } from '../../minor.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { emptyValidator } from '../../../../util/util';
import { Subscription } from 'rxjs';
import { AuthorizeService } from '../../../authorize/authorize.service';

export interface Note {
  id: number,
  policemanId: number,
  note: string
}

@Component({
  selector: 'app-notes',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './notes.component.html'
})
export class NotesComponent implements OnInit, OnDestroy {
  @Input({ required: true }) crimeId!: number;
  notes: Note[] = [];
  newNote = new FormControl('', emptyValidator);
  temporaryInvalidMark: boolean = false;
  subscriptions: Subscription[] = [];
  constructor(private minorService: MinorService, private authService: AuthorizeService) { }
  
  ngOnInit(): void {
    this.subscriptions = [
      this.minorService.getNotes(this.crimeId).subscribe(v => this.notes = v)
    ];
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }

  getPolicemanName(policemanId: number): string {
    let partner = this.minorService.
      getPartners(this.crimeId).
      getValue().
      find(v => v.id == policemanId);
    if (partner) {
      return partner.name;
    } else {
      return this.authService.self.getValue()?.name ?? '';
    }
  }

  createNote(): void {
    if (this.newNote.valid && this.newNote.value !== null) {
      this.minorService.createNote(this.crimeId, this.newNote.value);
      this.newNote.setValue('');
    } else {
      this.temporaryInvalidMark = true;
      setTimeout(() => this.temporaryInvalidMark = false, 3000);
    }
  }
}
