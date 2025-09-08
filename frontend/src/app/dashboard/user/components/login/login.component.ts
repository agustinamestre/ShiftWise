import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';

import { Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../interfaces/LoginRequest';
import { ToastrService } from 'ngx-toastr';
import { MaterialModule } from '../../../../material/material.module';
import AppRoutes from '../../../../common/routes';
import ErrorResponse from '../../../../models/ErrorResponse';

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
  private toastr = inject(ToastrService);

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
        recordar: this.loginForm.get('recordar')?.value,
      };

      console.log(credentials);

      this.authService.login(credentials).subscribe({
        next: () => {
          this.toastr.success('¡Inicio de sesión exitoso!');
          this.isLoading.set(false);
          this.router.navigate(['/']);
        },
        error: (error: ErrorResponse) => {
          this.isLoading.set(false);
          this.handleError(error);
        },
      });
    }
  }

  handleError(error: ErrorResponse) {
    console.log('ErrorResponse:', error);
    if (error.genericErrorMessage) {
      this.toastr.error(error.genericErrorMessage);
    } else {
      this.toastr.error(
        'Ocurrió un error al iniciar sesión. Verifica tus credenciales.'
      );
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
