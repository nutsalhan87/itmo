import { Component } from '@angular/core';
import { AuthService } from './auth.service';
import { NotifierService } from 'angular-notifier';
import { Router } from '@angular/router'

@Component({
    selector: 'auth',
    templateUrl: './auth.component.html'
})
export class AuthComponent {
    username: string = "";
    password: string = "";

    constructor(private authService: AuthService, private notifier: NotifierService, private router: Router) { }

    private validate(): boolean {
        if (this.username.trim() === "" || this.password.trim().length <= 3) {
            this.notifier.notify("warning", "Поля должны быть непустыми, а длина пароля более 3");
            return false;
        }
        return true;
    }

    signIn(): void {
        if (!this.validate()) {
            return;
        }
        this.authService.signIn(this.username, this.password, () => {this.router.navigateByUrl("/shotgame")});
    }

    signUp(): void {
        if (!this.validate()) {
            return;
        }
        this.authService.signUp(this.username, this.password, () => {this.router.navigateByUrl("/shotgame")});
    }
}