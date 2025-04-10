import { Routes } from '@angular/router';
import { MainComponent } from './main/main.component';
import { EditorComponent } from './editor/editor.component';
import { InstrumentsComponent } from './instruments/instruments.component';
import { SettingsComponent } from './settings/settings.component';

export const routes: Routes = [
    { title: "Таблица", path: "table/:page", component: MainComponent },
    { title: "Редкатор", path: "editor", component: EditorComponent },
    { title: "Редкатор", path: "editor/:id", component: EditorComponent },
    { title: "Инструменты", path: "instruments", component: InstrumentsComponent },
    { title: "Настройки", path: "settings", component: SettingsComponent },
    { path: "**", redirectTo: "/table/1" },
];
