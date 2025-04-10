import { Component, OnDestroy } from '@angular/core';
import { NotificationService, Notification } from './notification.service';
import { Subscription } from 'rxjs';

const lifetimeMs: number = 5000;
const maxNotifications: number = 4;

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [],
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.scss',
})
export class NotificationsComponent implements OnDestroy {
  notifications: [Notification, number][] = [];
  subscription: Subscription;
  
  constructor(notificationsService: NotificationService) {
    this.subscription = notificationsService.notifications.subscribe({
      next: notification => {
        if (this.notifications.length >= maxNotifications) {
          this.notifications.shift();
        }
        let timeoutID = setTimeout(() => this.notifications.shift(), lifetimeMs);
        this.notifications.push([notification, timeoutID]);
      },
    });
  }

  deleteNotification(idx: number) {
    let removed = this.notifications.splice(idx, 1);
    if (removed.length != 0) {
      clearTimeout(removed[0][1]);
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
