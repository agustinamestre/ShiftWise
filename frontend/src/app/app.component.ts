import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './core/layout/components/navbar/navbar.component';
import { FooterComponent } from './core/layout/components/footer/footer.component';
import { HomeComponent } from './core/layout/components/home/home.component';
import { UserComponent } from './dashboard/user/components/user/user.component';
import { LoginComponent } from './dashboard/user/components/login/login.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    NavbarComponent,
    FooterComponent,
    LoginComponent,
    UserComponent,
    HomeComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'ShiftWise-Frontend';
}
