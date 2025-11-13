import { Routes } from '@angular/router';
import AppRoutes from './common/routes';
import { HomeComponent } from './core/layout/components/home/home.component';
import { SearchJornadaComponent } from './dashboard/jornadas/components/search-jornada/search-jornada.component';
import { LoginComponent } from './dashboard/user/components/login/login.component';
import { UserComponent } from './dashboard/user/components/user/user.component';
import { CreateJornadaComponent } from './dashboard/jornadas/components/create-jornada/create-jornada.component';
import { ForgotPasswordModalComponent } from './dashboard/user/components/forgot-password-modal/forgot-password-modal.component';
import { ResetPasswordComponent } from './dashboard/user/components/reset-password/reset-password.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: AppRoutes.LOGIN, component: LoginComponent },
  { path: AppRoutes.NEW_USER, component: UserComponent },
  { path: AppRoutes.JORNADA_LIST, component: SearchJornadaComponent },
  { path: AppRoutes.NEW_JORNADA, component: CreateJornadaComponent },
  { path: AppRoutes.EDIT_USER, component: UserComponent },
  { path: AppRoutes.FORGOT_PASS, component: ForgotPasswordModalComponent },
  { path: AppRoutes.RESET_PASS, component: ResetPasswordComponent },
];
