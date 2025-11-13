import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { MaterialModule } from '../../../../material/material.module';
import AppRoutes from '../../../../common/routes';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { AuthService } from '../../../../dashboard/user/services/auth.service';
import { Observable } from 'rxjs';
import User from '../../../../dashboard/user/interfaces/User';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [MaterialModule, RouterLink, RouterModule, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NavbarComponent {
  appRoutes = AppRoutes;
  private authService = inject(AuthService);
  private router = inject(Router);

  currentUser: Observable<User | null> = this.authService.currentUser;

  logout(): void {
    this.authService.logout().subscribe({
      next: () => {
        this.router.navigate([`/${AppRoutes.LOGIN}`]);
      },
      error: () => {
        this.authService.clearSession();
        this.router.navigate([`/${AppRoutes.LOGIN}`]);
      },
    });
  }
}
