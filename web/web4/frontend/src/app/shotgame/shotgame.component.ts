import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { DataService } from '../data/data.service';
import { Shot } from '../data/shot';
import { Router } from '@angular/router'
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'shotgame',
    templateUrl: './shotgame.component.html'
})
export class ShotgameComponent implements OnInit {
    constructor(private dataService: DataService, private authService: AuthService, private router: Router, private notifier: NotifierService) { }
    
    ngOnInit(): void {
        this.dataService.updateShots();
    }

    sendShot(): void {
        if (this.x.toString().trim() === "" || this.y.toString().trim() === "" || this.r.toString().trim() === "") {
            this.notifier.notify("warning", "Поля должны быть непустыми, а также числами в пределах, указанных в плейсхолдере поля");
        } else {
            this.dataService.sendShot(this.x, this.y, this.r);
        }
    }

    exit(): void {
        this.authService.exit(() => { this.router.navigateByUrl(""); });
    }

    get shots(): Shot[] {
        return this.dataService.shots;
    }

    get x(): number {
        return this.dataService.currentShot.x;
    }

    set x(val: number) {
        this.dataService.currentShot.x = val;
    }

    get y(): number {
        return this.dataService.currentShot.y;
    }

    set y(val: number) {
        this.dataService.currentShot.y = val;
    }

    get r(): number {
        return this.dataService.currentShot.r;
    }

    set r(val: number) {
        this.dataService.currentShot.r = val;
    }
}