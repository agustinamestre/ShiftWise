import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../material/material.module';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import Jornada from '../../interface/Jornada';
import { CommonModule, Location } from '@angular/common';
import { RouterModule } from '@angular/router';
import AppRoutes from '../../../../common/routes';
import { JornadaService } from '../../services/jornada.service';
import { ToastrService } from 'ngx-toastr';
import { JornadaResponse } from '../../interface/JornadaResponse';

@Component({
  selector: 'app-search-jornada',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './search-jornada.component.html',
  styleUrl: './search-jornada.component.css',
})
export class SearchJornadaComponent implements OnInit {
  form!: FormGroup;
  jornadas: Array<Jornada> = [];
  appRoutes = AppRoutes;

  private toastr = inject(ToastrService);

  constructor(
    private jornadaService: JornadaService,
    private location: Location
  ) {
    this.form = new FormGroup({
      nroDocumento: new FormControl(''),
      apellido: new FormControl(''),
      fecha: new FormControl(''),
    });

    const path = this.location.path();
  }

  ngOnInit(): void {}

  search(): void {
    if (this.form.valid) {

      const fechaValue = this.form.get('fecha')?.value;
      const fecha = fechaValue ? fechaValue.toISOString().split('T')[0] : undefined;
      const doc = this.form.get('nroDocumento')?.value;
      const apellido = this.form.get('apellido')?.value;

      this.jornadaService.getJornadas(doc, fecha, apellido).subscribe({
        next: (response: JornadaResponse[]) => {

          if (response.length === 0) {
            this.toastr.warning('No se encontraron jornadas');
          }

          if (response) {
            this.jornadas = response.map((jornadaData) => {
              const jornada: Jornada = {
                idJornada: jornadaData.idJornada,
                nroDocumento: jornadaData.nroDocumento,
                nombreCompleto: jornadaData.nombreCompleto,
                fecha: jornadaData.fecha,
                concepto: jornadaData.concepto,
                horasTrabajadas: jornadaData.horasTrabajadas,
              };
              return jornada;
            });
          }
        },
        error: (error) => {
          console.error('Error obteniendo jornadas:', error);
          this.jornadas = [];
        },
      });
    }
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
