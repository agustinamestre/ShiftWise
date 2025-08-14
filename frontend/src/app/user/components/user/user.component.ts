import { Component, OnInit } from '@angular/core';

import { MaterialModule } from '../../../material/material.module';
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
import { HttpErrorResponse } from '@angular/common/http';
import { mapBackendErrorToErrorResponse } from '../../../models/error-utils';
import { UserResponse } from '../../interfaces/UserResponse';
import ErrorResponse from '../../../models/ErrorResponse';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule, RouterModule],
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
  userId = '';
  selectedFileName: string = '';
  selectedFileUrl: string | null = null;
  base64Image: string | null = null;

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
    } else if (path.includes('edit')) {
      this.getUser();
      this.isEdition = true;
      this.title = 'Editar Usuario';
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
      fotoBase64: this.base64Image,
      password: this.form.get('password')?.value,
    };

    if (this.isNew) {
      this.userService.createUser(user).subscribe({
        next: (response) => {
          this.toastr.success('Empleado creado exitosamente!');
          this.volver();
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

  private getUser() {
    this.userService
      .getUserById(this.userId)
      .subscribe((userData: UserResponse) => {
        if (userData) {
          this.user = {
            nroDocumento: userData.nroDocumento,
            nombre: userData.nombre,
            apellido: userData.apellido,
            email: userData.email,
            fechaNacimiento: userData.fechaNacimiento,
            fechaIngreso: userData.fechaIngreso,
            password: '',
          };

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

    // if (this.isDetail) {
    //   this.form.disable();
    // }
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

        console.log('Base64:', this.base64Image);
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
}
