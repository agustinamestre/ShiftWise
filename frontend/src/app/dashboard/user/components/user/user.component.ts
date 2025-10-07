import { Component, inject, OnInit } from '@angular/core';

import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import UserRequest from '../../interfaces/UserRequest';
import User from '../../interfaces/User';
import { UserService } from '../../services/user.service';
import { CommonModule, Location } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { UserResponse } from '../../interfaces/UserResponse';
import { MaterialModule } from '../../../../material/material.module';
import ErrorResponse from '../../../../models/ErrorResponse';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent {
  form!: FormGroup;
  user: User | null = null;
  today: Date = new Date();
  isNew = false;
  isEdition = false;
  isDetail = false;
  title: string = '';
  userId = '';
  selectedFileName: string = '';
  selectedFileUrl: string | null = null;
  base64Image: string | null = null;

  private router = inject(Router);
  private authService = inject(AuthService);

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private location: Location,
    private readonly fb: FormBuilder,
    private toastr: ToastrService
  ) {
    const path = this.location.path();

    this.route.params.subscribe((params) => {
      this.userId = params['id'];
    });

    if (path.includes('new')) {
      this.isNew = true;
      this.title = 'Registrarse';
      this.buildForm();
    } else if (path.includes('edit')) {
      this.isEdition = true;
      this.title = 'Editar Usuario';
      this.buildForm();
      this.getUser();
    }
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
      password: [
        '',
        this.isNew
          ? [
              Validators.required,
              Validators.minLength(8),
              Validators.pattern(/^(?=.*[A-Z])(?=.*\d).{8,}$/),
            ]
          : [],
      ],
      foto: [''],
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      return;
    }

    const user: UserRequest = {
      nombre: this.form.get('nombre')?.value,
      apellido: this.form.get('apellido')?.value,
      nroDocumento: this.form.get('nroDocumento')?.value,
      email: this.form.get('email')?.value,
      fechaNacimiento: this.form.get('fechaNacimiento')?.value,
      fechaIngreso: this.form.get('fechaIngreso')?.value,
      fotoBase64: this.base64Image,
      password: this.form.get('password')?.value,
    };

    if (this.isNew) {
      this.userService.createUser(user).subscribe({
        next: (response) => {
          this.toastr.success('Empleado creado exitosamente!');
          this.authService.setCurrentUser({
            id: response.id,
            nroDocumento: response.nroDocumento,
            nombre: response.nombre,
            apellido: response.apellido,
            email: response.email,
            fechaNacimiento: response.fechaNacimiento,
            fechaIngreso: response.fechaIngreso,
            fotoBase64: response.foto,
            password: '',
          });
          const credentials = {
            nroDocumento: user.nroDocumento,
            password: user.password,
            recordar: false,
          };
          this.authService.login(credentials).subscribe({
            next: () => {
              this.router.navigate(['/jornadas']);
            },
          });
        },
        error: (error: ErrorResponse) => {
          this.handleError(error);
        },
      });
    } else if (this.isEdition) {
      this.userService.updateUser(this.userId, user).subscribe({
        next: () => {
          this.toastr.success('Empleado actualizado exitosamente!');
          this.volver();
        },
        error: (err: ErrorResponse) => {
          this.handleError(err);
        },
      });
    }
  }

  getUser() {
    this.userService
      .getUserById(this.userId)
      .subscribe((userData: UserResponse) => {
        if (userData) {
          this.user = {
            id: userData.id,
            nroDocumento: userData.nroDocumento,
            nombre: userData.nombre,
            apellido: userData.apellido,
            email: userData.email,
            fechaNacimiento: userData.fechaNacimiento,
            fechaIngreso: userData.fechaIngreso,
            fotoBase64: userData.foto,
            password: '',
          };
          this.base64Image = userData.foto ?? null;

          if (userData.foto) {
            this.selectedFileUrl = `data:image/jpeg;base64,${userData.foto}`;
          }

          this.fillForm(this.user!);
        }
      });
  }

  private fillForm(user: User) {
    this.form.get('nroDocumento')?.setValue(user.nroDocumento);
    this.form.get('nombre')?.setValue(user.nombre);
    this.form.get('apellido')?.setValue(user.apellido);
    this.form.get('email')?.setValue(user.email);
    this.form.get('fechaNacimiento')?.setValue(user.fechaNacimiento);
    this.form.get('fechaIngreso')?.setValue(user.fechaIngreso);

    if (this.isDetail) {
      this.form.disable();
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.selectedFileName = file.name;

      const reader = new FileReader();

      reader.onload = () => {
        const result = reader.result as string;
        this.selectedFileUrl = result;
        this.base64Image = result.split(',')[1];
      };
      reader.readAsDataURL(file);
    }
  }

  handleError(error: ErrorResponse) {
    console.log('ErrorResponse:', error);
    if (error.genericErrorMessage) {
      this.toastr.error(error.genericErrorMessage);
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

  volver() {
    this.location.back();
  }

  onCancel(): void {
    this.form.reset(this.user);
    this.selectedFileUrl = this.user?.fotoBase64
      ? `data:image/jpeg;base64,${this.user.fotoBase64}`
      : null;
  }
}
