import { ChangeDetectionStrategy, Component } from '@angular/core';
import { MaterialModule } from '../../../../material/material.module';
import AppRoutes from '../../../../common/routes';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [MaterialModule, RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NavbarComponent {
  appRoutes = AppRoutes;
}
