import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export interface Notification {
  id: number,
  header: string,
  body?: string,
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private idGenerator: number = 0;
  notifications: Subject<Notification> = new Subject();
  constructor() { }

  send(header: string, body?: string) {
    this.notifications.next({
      id: this.idGenerator,
      header,
      body
    });
    this.idGenerator += 1;
  }
}
