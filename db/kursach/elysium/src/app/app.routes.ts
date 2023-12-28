import { Routes } from '@angular/router';
import { AuthorizeComponent } from './authorize/authorize.component';
import { MinorComponent } from './minor/minor.component';
import { MajorComponent } from './major/major.component';
import { majorGuard, minorGuard } from './authorize/authorize.service';

export const routes: Routes = [
    {title: 'Raphaël Ambrosius Costeau', path: 'authorize', component: AuthorizeComponent},
    {title: 'Detective', path: 'minor', component: MinorComponent, canActivate: [minorGuard]},
    {title: 'Major', path: 'major', component: MajorComponent, canActivate: [majorGuard]},
    {path: '**', redirectTo: '/authorize'}
];
