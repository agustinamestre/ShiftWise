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

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RegisterComponent implements OnInit {
  form!: FormGroup;
  user: User | null = null;

  constructor(
    private userService: UserService,
    private router: Router,
    private readonly fb: FormBuilder
  ) {}

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

    this.userService.createUser(user).subscribe({
      next: (response) => {
        console.log('Usuario creado exitosamente', response);
      },
      error: (error) => {
        console.error('Error al crear el usuario', error);
      },
    });
  }
}
