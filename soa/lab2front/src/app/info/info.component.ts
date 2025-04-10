import { Component, input } from '@angular/core';
import { NgxFloatUiModule, NgxFloatUiTriggers, NgxFloatUiPlacements } from 'ngx-float-ui';

@Component({
  selector: 'app-info',
  standalone: true,
  imports: [NgxFloatUiModule],
  templateUrl: './info.component.html',
  styleUrl: './info.component.scss'
})
export class InfoComponent {
  NgxFloatUiTriggers = NgxFloatUiTriggers;
  NgxFloatUiPlacements = NgxFloatUiPlacements;
  body = input.required<string>();
}
// TODO: style
