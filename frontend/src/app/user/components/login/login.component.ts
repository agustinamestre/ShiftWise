import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';

import { MaterialModule } from '../../../material/material.module';
import { Router, RouterModule } from '@angular/router';
import AppRoutes from '../../../common/routes';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../interfaces/LoginRequest';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [MaterialModule, RouterModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  appRoutes = AppRoutes;

  isLoading = signal(false);
  hidePassword = signal(true);

  loginForm: FormGroup = this.fb.group({
    documento: ['', [Validators.required, Validators.minLength(6)]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    recordar: [false],
  });

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.isLoading.set(true);

      const credentials: LoginRequest = {
        nroDocumento: this.loginForm.get('documento')?.value,
        password: this.loginForm.get('password')?.value,
      };

      this.authService.login(credentials).subscribe({
        next: (response) => {
          console.log('Login exitoso:', response);
          this.isLoading.set(false);

          this.router.navigate(['/']);
        },
      });
    }
  }

  togglePasswordVisibility(): void {
    this.hidePassword.set(!this.hidePassword());
  }

  get documento() {
    return this.loginForm.get('documento');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
