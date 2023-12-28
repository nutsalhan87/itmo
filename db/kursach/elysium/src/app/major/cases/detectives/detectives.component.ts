import { Component, Input } from '@angular/core';
import { Case, DetectiveStatus } from '../cases.component';
import { MajorService } from '../../major.service';
import { Subordinate } from '../../subordinates/subordinates.component';

@Component({
  selector: 'app-detectives',
  standalone: true,
  imports: [],
  templateUrl: './detectives.component.html'
})
export class DetectivesComponent {
  @Input({ required: true }) case!: Case;
  constructor(private majorService: MajorService) {}

  isUnchangable(cas: Case, detective: DetectiveStatus): boolean {
    return cas.state == 'close' || !detective.owns;
  }

  changeDetectiveStatus(detective: Subordinate, newStatus: 'assigned' | 'removed'): void {
    this.majorService.changeDetectiveStatus(detective.id, this.case.id, newStatus);
  }
}
