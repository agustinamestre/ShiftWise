import { Routes } from '@angular/router';
import AppRoutes from './common/routes';
import { LoginComponent } from './user/components/login/login.component';
import { HomeComponent } from './core/layout/components/home/home.component';
import { UserComponent } from './user/components/user/user.component';
import { SearchJornadaComponent } from './dashboard/jornadas/search-jornada/search-jornada.component';
import { CreateJornadaComponent } from './dashboard/jornadas/create-jornada/create-jornada.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: AppRoutes.LOGIN, component: LoginComponent },
  { path: AppRoutes.NEW_USER, component: UserComponent },
  { path: AppRoutes.JORNADA_LIST, component: SearchJornadaComponent },
  { path: AppRoutes.NEW_JORNADA, component: CreateJornadaComponent },
];
