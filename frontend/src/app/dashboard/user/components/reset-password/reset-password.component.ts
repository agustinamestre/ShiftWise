import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { MaterialModule } from '../../../../material/material.module';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import AppRoutes from '../../../../common/routes';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [MaterialModule, FormsModule],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ResetPasswordComponent implements OnInit {
  resetPasswordForm!: FormGroup;
  isLoading = signal(false);
  hidePassword = signal(true);
  hideConfirmPassword = signal(true);
  token: string | null = null;
  appRoutes = AppRoutes;

  private toastr = inject(ToastrService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private http = inject(HttpClient);

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.token = params['token'];
      if (!this.token) {
        this.toastr.error('Token de restablecimiento no válido', 'Error');
        this.router.navigate(['/login']);
      }
    });

    this.resetPasswordForm = this.fb.group(
      {
        password: ['', [Validators.required]],
        confirmPassword: ['', [Validators.required]],
      },
      {
        validators: this.passwordMatchValidator,
      }
    );
  }

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');

    if (!password || !confirmPassword) {
      return null;
    }

    if (confirmPassword.value === '') {
      return null;
    }

    if (password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    } else {
      // Limpiar el error si las contraseñas coinciden
      const errors = confirmPassword.errors;
      if (errors) {
        delete errors['passwordMismatch'];
        confirmPassword.setErrors(
          Object.keys(errors).length > 0 ? errors : null
        );
      }
      return null;
    }
  }

  togglePasswordVisibility(): void {
    this.hidePassword.set(!this.hidePassword());
  }

  toggleConfirmPasswordVisibility(): void {
    this.hideConfirmPassword.set(!this.hideConfirmPassword());
  }

  get password() {
    return this.resetPasswordForm.get('password');
  }

  get confirmPassword() {
    return this.resetPasswordForm.get('confirmPassword');
  }

  onSubmit() {
    if (this.password !== this.confirmPassword) {
      //this.snackBar.open('Las contraseñas no coinciden', 'Cerrar', { duration: 3000 });
      return;
    }
    if (this.password.length < 6) {
      //this.snackBar.open('La contraseña debe tener al menos 6 caracteres', 'Cerrar', { duration: 3000 });
      return;
    }
    if (this.token) {
      this.http
        .post('http://localhost:8080/shiftwise/v1/auth/reset-password', {
          token: this.token,
          password: this.password,
        })
        .subscribe({
          next: () => {
            this.toastr.success('Contraseña restablecida con éxito');
            this.router.navigate(['/login']);
          },
          error: (error) => {
            //this.snackBar.open(error.error.errorMessage || 'Error al restablecer la contraseña', 'Cerrar', { duration: 3000 });
          },
        });
    } else {
      //this.snackBar.open('Token inválido', 'Cerrar', { duration: 3000 });
    }
  }

  cancel() {
    this.router.navigate(['/login']);
  }
}
