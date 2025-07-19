import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import Jornada from '../interface/Jornada';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-search-jornada',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule],
  templateUrl: './search-jornada.component.html',
  styleUrl: './search-jornada.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchJornadaComponent implements OnInit {
  form!: FormGroup;
  jornadas: Array<Jornada> = [];

  ngOnInit(): void {
    this.form = new FormGroup({
      nroDocumento: new FormControl(''),
      fecha: new FormControl(''),
    });
  }
}
