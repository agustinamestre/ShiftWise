import { ChangeDetectionStrategy, Component } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import AppRoutes from '../../../common/routes';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [MaterialModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NavbarComponent {
  appRoutes = AppRoutes;
}
