import { RouterModule, Routes } from '@angular/router';
import AppRoutes from './common/routes';
import { LoginComponent } from './user/components/login/login.component';
import { HomeComponent } from './core/layout/home/home.component';
import { RegisterComponent } from './user/components/user/user.component';
import { NgModule } from '@angular/core';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: AppRoutes.LOGIN, component: LoginComponent },
  { path: AppRoutes.NEW_USER, component: RegisterComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
