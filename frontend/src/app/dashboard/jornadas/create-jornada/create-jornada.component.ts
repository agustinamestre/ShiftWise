import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import Jornada from '../interface/Jornada';
import { MaterialModule } from '../../../material/material.module';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import User from '../../../user/interfaces/User';
import { Concept } from '../interface/Concept';
import { ConceptService } from '../services/concept.service';
import { ConceptResponse } from '../interface/ConceptResponse';
import { UserService } from '../../../user/services/user.service';
import { UserResponse } from '../../../user/interfaces/UserResponse';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-jornada',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './create-jornada.component.html',
  styleUrl: './create-jornada.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CreateJornadaComponent implements OnInit {
  form!: FormGroup;
  jornadas: Array<Jornada> = [];
  concepts: Array<Concept> = [];
  users: Array<User> = [];

  constructor(
    private conceptService: ConceptService,
    private userService: UserService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.getConcepts();
    this.getUsers();

    this.form = new FormGroup({
      idUser: new FormControl(''),
      idConcepto: new FormControl(''),
      fecha: new FormControl(''),
      horasTrabajadas: new FormControl(''),
    });
  }

  getConcepts() {
    this.conceptService
      .getConcepts()
      .subscribe((response: ConceptResponse[]) => {
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
      });
  }

  getUsers() {
    this.userService.getUsers().subscribe(
      (users: UserResponse[]) => {
        if (users.length === 0) {
          this.toastr.error(
            'No se encontraron usuarios en el sistema. Para proceder, asegurarse de cargar alguno.'
          );
          return;
        }

        this.users = users.map((userData) => {
          const user: User = {
            nroDocumento: userData.nroDocumento,
            nombre: userData.nombre,
            apellido: userData.apellido,
            email: userData.email,
            fechaNacimiento: userData.fechaNacimiento,
            fechaIngreso: userData.fechaIngreso,
            fechaCreacion: userData.fechaCreacion,
          };
          return user;
        });
      },
      (err: Error) => {
        this.toastr.error(err.message);
      }
    );
  }
}
