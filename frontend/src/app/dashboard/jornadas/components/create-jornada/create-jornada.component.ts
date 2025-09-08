import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import Jornada from '../../interface/Jornada';
import { MaterialModule } from '../../../../material/material.module';
import { Concept } from '../../interface/Concept';
import { ConceptService } from '../../services/concept.service';
import { ConceptResponse } from '../../interface/ConceptResponse';
import User from '../../../user/interfaces/User';
import { UserService } from '../../../user/services/user.service';
import { UserResponse } from '../../../user/interfaces/UserResponse';
import { JornadaService } from '../../services/jornada.service';
import { JornadaRequest } from '../../interface/JornadaRequest';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import ErrorResponse from '../../../../models/ErrorResponse';

@Component({
  selector: 'app-create-jornada',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './create-jornada.component.html',
  styleUrl: './create-jornada.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CreateJornadaComponent implements OnInit {
  private conceptService = inject(ConceptService);
  private userService = inject(UserService);
  private jornadaService = inject(JornadaService);
  private toastr = inject(ToastrService);
  private router = inject(Router);

  form!: FormGroup;
  jornadas: Array<Jornada> = [];
  concepts: Array<Concept> = [];
  users: Array<User> = [];

  isAnyFilterEntered = false;

  constructor(private location: Location) {
    const path = this.location.path();
  }

  ngOnInit(): void {
    this.initForm();
    this.getConcepts();
    this.getUsers();

    this.form.valueChanges.subscribe((v) => {
      this.isAnyFilterEntered = Object.values(this.form.value).some((v) => !!v);
    });

    this.form.get('idConcepto')!.valueChanges.subscribe((conceptId) => {
      if (conceptId === 3) {
        this.form.get('horasTrabajadas')?.reset();
        this.form.get('horasTrabajadas')?.setValue(undefined);
        this.form.get('horasTrabajadas')?.disable();
      } else {
        this.form.get('horasTrabajadas')?.enable();
      }
    });
  }

  initForm() {
    this.form = new FormGroup({
      idUser: new FormControl('', [Validators.required]),
      idConcepto: new FormControl('', [Validators.required]),
      fecha: new FormControl('', [Validators.required]),
      horasTrabajadas: new FormControl('', [Validators.required]),
    });
  }

  getConcepts() {
    this.conceptService.getConcepts().subscribe({
      next: (response: ConceptResponse[]) => {
        if (response) {
          this.concepts = response.map((conceptData) => {
            const concept = new Concept();
            concept.id = conceptData.id;
            concept.nombre = conceptData.nombre;
            concept.laborable = conceptData.laborable;
            concept.hsMinimo = conceptData.hsMinimo;
            concept.hsMaximo = conceptData.hsMaximo;
            return concept;
          });
        }
      },
      error: () => {
        this.toastr.error('Error al cargar conceptos');
      },
    });
  }

  getUsers() {
    this.userService.getUsers().subscribe({
      next: (users: UserResponse[]) => {
        if (users.length === 0) {
          this.toastr.error(
            'No se encontraron usuarios en el sistema. Para proceder, asegurarse de cargar alguno.'
          );
          return;
        }
        this.users = users.map((userData) => ({
          id: userData.id,
          nroDocumento: userData.nroDocumento,
          nombre: userData.nombre,
          apellido: userData.apellido,
          email: userData.email,
          fechaNacimiento: userData.fechaNacimiento,
          fechaIngreso: userData.fechaIngreso,
          fechaCreacion: userData.fechaCreacion,
        }));
      },
      error: (err: Error) => {
        this.toastr.error('Error al cargar usuarios: ' + err.message);
      },
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      const request: JornadaRequest = {
        idUser: this.form.get('idUser')?.value,
        idConcepto: this.form.get('idConcepto')?.value,
        fecha: this.form.get('fecha')?.value,
        horasTrabajadas: Number(this.form.get('horasTrabajadas')?.value),
      };

      this.jornadaService.createJornada(request).subscribe({
        next: () => {
          this.toastr.success('Jornada creada exitosamente');
          this.router.navigate(['/jornadas']);
        },
        error: (error: ErrorResponse) => {
          this.handleError(error);
        },
      });
    } else {
      this.toastr.error('Por favor, completa todos los campos requeridos.');
    }
  }

  handleError(error: ErrorResponse) {
    if (error.genericErrorMessage) {
      this.toastr.error(error.genericErrorMessage);
    } else {
      this.toastr.error('Ocurrió un error inesperado.');
    }
  }

  onCancel(): void {
    this.form.reset();
  }

  volver() {
    this.location.back();
  }
}
