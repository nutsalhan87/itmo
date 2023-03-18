import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { NotifierService } from 'angular-notifier';
import { apiBase } from '../app.module';

@Injectable()
export class AuthService implements CanActivate {
    constructor(private http: HttpClient, private notifier: NotifierService) { }

    private async tokenStatus(onError: (error: any) => void): Promise<boolean> {
        const token: string | null = localStorage.getItem("x-api-token");
        if (token === null) {
            return false;
        }
        let body = new FormData();
        body.append("x-api-token", token);
        try {
            const response = await this.http.post(`${apiBase}/api/sign/by-token`, body).toPromise();
            return true;
        } catch (e) {
            onError(e);
            return false;
        }
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
        return this.tokenStatus((error: any) => { this.notifier.notify("error", error.error.message); });
    }

    signIn(username: string, password: string, onSuccess: () => void): void {
        let body = new FormData();
        body.append("username", username);
        body.append("password", password);
        this.http.post(`${apiBase}/api/sign/in`, body).subscribe(
            (data: any) => {
                localStorage.setItem("x-api-token", data["x-api-token"]);
                onSuccess();
            },
            (error: any) => {
                this.notifier.notify("error", error.error.message);
            }
        );
    }

    signUp(username: string, password: string, onSuccess: () => void): void {
        let body = new FormData();
        body.append("username", username);
        body.append("password", password);
        this.http.post(`${apiBase}/api/sign/up`, body).subscribe(
            (data: any) => {
                localStorage.setItem("x-api-token", data["x-api-token"]);
                onSuccess();
            },
            (error: any) => {
                this.notifier.notify("error", error.error.message);
            }
        );
    }

    exit(onSuccess: () => void): void {
        let body = new FormData();
        body.append("x-api-token", localStorage.getItem("x-api-token"));
        this.http.post(`${apiBase}/api/sign/exit`, body).subscribe(
            (data: any) => {
                onSuccess();
            },
            (error: any) => {
                this.notifier.notify("error", error.error.message);
            }
        );
    }
}