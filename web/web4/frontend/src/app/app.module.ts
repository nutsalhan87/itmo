import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { DataService } from './data/data.service';
import { TargetComponent } from './target/target.component';
import { ShotgameComponent } from './shotgame/shotgame.component';
import { Routes, RouterModule } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { AuthService } from './auth/auth.service';
import { NotifierModule, NotifierService } from 'angular-notifier';

export const apiBase: string = "";

const appRoutes: Routes = [
    { path: '', component: AuthComponent },
    { path: 'shotgame', component: ShotgameComponent, canActivate: [AuthService] },
    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [BrowserModule, FormsModule, HttpClientModule, RouterModule.forRoot(appRoutes), NotifierModule],
    declarations: [AppComponent, TargetComponent, ShotgameComponent, AuthComponent],
    bootstrap: [AppComponent],
    providers: [DataService, AuthService, NotifierService]
})
export class AppModule { }