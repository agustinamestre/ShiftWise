import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';

import { MaterialModule } from '../../../material/material.module';
import { UserService } from '../../services/User.service';
import { Router } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import UserRequest from '../../interfaces/UserRequest';
import User from '../../interfaces/User';
import { CommonModule, Location } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import { mapBackendErrorToErrorResponse } from '../../../models/error-utils';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule],
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  form!: FormGroup;
  user: User | null = null;
  today: Date = new Date();
  isNew = false;
  isEdition = false;
  isDetail = false;
  title: string = '';
  selectedFileName: string = '';
  selectedFileUrl: string | null = null;

  constructor(
    private userService: UserService,
    private router: Router,
    private location: Location,
    private readonly fb: FormBuilder,
    private toastr: ToastrService
  ) {
    const path = this.location.path();

    if (path.includes('new')) {
      this.isNew = true;
      this.title = 'Registrarse';
    }
  }

  ngOnInit(): void {
    this.buildForm();
  }

  private buildForm() {
    this.form = this.fb.group({
      nroDocumento: [
        this.user?.nroDocumento ?? undefined,
        [Validators.required, Validators.max(99999999)],
      ],
      nombre: [this.user?.nombre ?? '', Validators.required],
      apellido: [this.user?.apellido ?? '', Validators.required],
      email: [this.user?.email ?? '', [Validators.required, Validators.email]],
      fechaNacimiento: [this.user?.fechaNacimiento ?? '', Validators.required],
      fechaIngreso: [this.user?.fechaIngreso ?? '', Validators.required],
      password: ['', Validators.required],
      foto: [''],
    });
  }

  onSubmit() {
    console.log('Formulario enviado', this.form.value);

    if (this.form.invalid) {
      return;
    }

    const user: UserRequest = {
      nombre: this.form.get('nombre')?.value,
      apellido: this.form.get('apellido')?.value,
      nroDocumento: this.form.get('nroDocumento')?.value,
      email: this.form.get('email')?.value,
      fechaNacimiento: this.form
        .get('fechaNacimiento')
        ?.value.toISOString()
        .split('T')[0],
      fechaIngreso: this.form
        .get('fechaIngreso')
        ?.value.toISOString()
        .split('T')[0],
      fotoBase64: this.form.get('foto')?.value,
      password: this.form.get('password')?.value,
    };

    if (this.isNew) {
      this.userService.createUser(user).subscribe({
        next: (response) => {
          this.toastr.success('Empleado creado exitosamente!');
        },
        error: (err: HttpErrorResponse) => {
          this.handleError(err);
        },
      });
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.selectedFileName = file.name;

      const reader = new FileReader();
      reader.onload = () => {
        this.selectedFileUrl = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  handleError(error: HttpErrorResponse) {
    if (error.error && error.error.error) {
      const adaptedError = mapBackendErrorToErrorResponse(
        error.error,
        error.status
      );
      this.toastr.error(adaptedError.genericErrorMessage);
    } else {
      this.toastr.error('Ocurrió un error inesperado.');
    }
  }

  getFormControlErrorMessage(name: string) {
    const formControlErrors = this.form.get(name)?.errors;

    return formControlErrors
      ? formControlErrors['errorMessage']
      : 'Invalid value';
  }

  onDocumentNumberKeyDown(e: any) {
    if (
      !(
        (e.keyCode > 95 && e.keyCode < 106) ||
        (e.keyCode > 47 && e.keyCode < 58) ||
        e.keyCode == 8
      )
    ) {
      e.preventDefault();
    }
  }
}
