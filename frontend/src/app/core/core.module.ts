import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { FooterComponent } from './layout/footer/footer.component';


@NgModule({
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class CoreModule { }
