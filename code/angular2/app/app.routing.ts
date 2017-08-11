
import { Routes, RouterModule } from "@angular/router";

import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { HomeComponent } from "./home/home.component";
import { HistoryComponent } from "./home/history/history.component";

const appRoutes: Routes = [
        {path: 'home', component: HomeComponent},
        {path: 'login', component: LoginComponent},
        {path: 'register', component: RegisterComponent},
        {path: 'history', component: HistoryComponent},
        {path: '', redirectTo: '/login', pathMatch: 'full'}
    ];

export const routing = RouterModule.forRoot(appRoutes);