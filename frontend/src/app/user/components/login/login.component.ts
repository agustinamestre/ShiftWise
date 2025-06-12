import { ChangeDetectionStrategy, Component } from '@angular/core';

import { MaterialModule } from '../../../material/material.module';
import { RouterModule } from '@angular/router';
import AppRoutes from '../../../common/routes';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [MaterialModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  appRoutes = AppRoutes;
}
