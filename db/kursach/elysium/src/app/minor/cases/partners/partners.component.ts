import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MinorService } from '../../minor.service';
import { Subscription } from 'rxjs';

export interface Partner {
  id: number,
  name: string,
  series: string,
  dateOfIssue: string,
  status: 'assigned' | 'removed'
}

@Component({
  selector: 'app-partners',
  standalone: true,
  imports: [],
  templateUrl: './partners.component.html'
})
export class PartnersComponent implements OnInit, OnDestroy {
  @Input({ required: true }) crimeId!: number;
  partners: Partner[] = [];
  subscriptions: Subscription[] = [];
  constructor(private minorService: MinorService) { }
  
  ngOnInit(): void {
    this.subscriptions = [
      this.minorService.getPartners(this.crimeId).subscribe(v => this.partners = v)
    ];
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(v => v.unsubscribe());
  }
}
