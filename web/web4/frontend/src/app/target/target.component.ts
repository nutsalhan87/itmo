import { Component } from '@angular/core';
import { DataService } from '../data/data.service';

@Component({
    selector: 'target',
    templateUrl: './target.component.html'
})
export class TargetComponent {
    realPointX: number = -10;
    realPointY: number = -10;

    constructor(private dataService: DataService) { }

    get targetScale(): number {
        const width = window.screen.width;
        if (width >= 450) {
            return 3 / 5;
        } else {
            return width / 600 * 0.8;
        }
    }

    get shots() {
        return this.dataService.shots;
    }

    get r() {
        return this.dataService.currentShot.r;
    }

    cxAttribute(x: number, r: number): number {
        return x * 200 / r + 300;
    }

    cyAttribute(y: number, r: number): number {
        return 300 - y * 200 / r;
    }

    moveRealPoint($event: MouseEvent): void {
        this.realPointX = $event.offsetX / this.targetScale;
        this.realPointY = $event.offsetY / this.targetScale;
    }

    setCoordinates($event: MouseEvent): void {
        const fitOffsetX: number = $event.offsetX / this.targetScale;
        const fitOffsetY: number = $event.offsetY / this.targetScale;
        const x: number = (fitOffsetX - 300) * this.dataService.currentShot.r / 200;
        const y: number = (300 - fitOffsetY) * this.dataService.currentShot.r / 200;
        this.dataService.currentShot.x = Math.round(x * 1e4) / 1e4;
        this.dataService.currentShot.y = Math.round(y * 1e4) / 1e4;
    }
}