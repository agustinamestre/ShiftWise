import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { MaterialModule } from '../../../../material/material.module';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reset-password-modal',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule],
  templateUrl: './forgot-password-modal.component.html',
  styleUrl: './forgot-password-modal.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ForgotPasswordModalComponent {
  forgotPasswordForm!: FormGroup;
  isLoading = signal(false);

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<ForgotPasswordModalComponent>,
    private toastr: ToastrService
  ) {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
    });
  }

  get email() {
    return this.forgotPasswordForm.get('email');
  }

  onSubmit() {
    if (this.forgotPasswordForm.valid) {
      this.isLoading.set(true);
      this.authService
        .forgotPassword(this.forgotPasswordForm.value.email)
        .subscribe({
          next: () => {
            this.isLoading.set(false);
            this.toastr.success('Correo de restablecimiento enviado');
            this.dialogRef.close();
          },
          error: () => {
            this.isLoading.set(false);
            this.toastr.error('Error al enviar el correo');
          },
        });
    }
  }

  onCancel() {
    this.dialogRef.close();
  }
}
