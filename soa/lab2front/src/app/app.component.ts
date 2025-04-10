import { Component } from '@angular/core';
import { APP_BASE_HREF, CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { NotificationsComponent } from "./notifications/notifications.component";
import { SettingsComponent } from "./settings/settings.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, NotificationsComponent, SettingsComponent],
  providers: [{provide: APP_BASE_HREF, useValue: "/"}],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  constructor() { }
}
