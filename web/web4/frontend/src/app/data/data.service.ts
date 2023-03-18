import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Shot } from './shot';
import { Coordinates } from './coordinates';
import { NotifierService } from 'angular-notifier';
import { apiBase } from '../app.module';

@Injectable()
export class DataService {
    currentShot: Coordinates = new Coordinates();
    shots: Shot[] = [];

    constructor(private http: HttpClient, private notifier: NotifierService) {}

    updateShots(): void {
        let body: FormData = new FormData();
        body.set("x-api-token", `${localStorage.getItem("x-api-token")}`);
        this.http.post(`${apiBase}/api/shots/get`, body).subscribe({next: (data: Shot[]) => {
            this.shots = [];
            for (let shot of data) {
                this.shots.push(shot);
            }
        }});
    }

    sendShot(x: number, y: number, r: number): void {
        let body: FormData = new FormData();
        body.set("x", `${x}`);
        body.set("y", `${y}`);
        body.set("r", `${r}`);
        body.set("x-api-token", `${localStorage.getItem("x-api-token")}`);
        this.http.post(`${apiBase}/api/shots/send`, body).subscribe(
            (data: any) => {
                this.shots.push(data);
            },
            (error: any) => {
                this.notifier.notify("error", error.error.message);
            }
        );
    }
}