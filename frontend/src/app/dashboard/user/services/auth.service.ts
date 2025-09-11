import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { LoginRequest } from "../interfaces/LoginRequest";
import { environment } from "../../../../environments/environment";
import { UserService } from "./user.service";
import User from "../interfaces/User";
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  endpoint: string = 'auth';
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY = 'current_user';

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(
    this.hasToken()
  );
  public isAuthenticated = this.isAuthenticatedSubject.asObservable();

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser = this.currentUserSubject.asObservable();
  private router = inject(Router);

  constructor(private http: HttpClient, private userService: UserService) {
    console.log('AuthService initialized, hasToken:', this.hasToken());
    if (this.hasToken()) {
      this.loadCurrentUser();
    }

  }

  login(credentials: LoginRequest): Observable<any> {
    let url = `${environment.api}${this.endpoint}${'/login'}`;

    const headers = new HttpHeaders({
      Authorization:
        'Basic ' + btoa(`${credentials.nroDocumento}:${credentials.password}`),
    });

    return this.http.post(url, null, { headers, observe: 'response' }).pipe(
      tap((response: HttpResponse<any>) => {
        const token = response.headers.get('X-Auth-Token');
        if (token) {
          this.saveToken(token, credentials.recordar);
          this.isAuthenticatedSubject.next(true);
          this.loadCurrentUser(credentials.nroDocumento);
        }
      })
    );
  }

  private loadCurrentUser(nroDocumento?: string): void {
    if (nroDocumento) {
      this.userService.getUsers().subscribe({
        next: (users) => {
          const user = users.find((u) => u.nroDocumento === nroDocumento);
          if (user) {
            this.currentUserSubject.next({
              id: user.id,
              nroDocumento: user.nroDocumento,
              nombre: user.nombre,
              apellido: user.apellido,
              email: user.email,
              fechaNacimiento: user.fechaNacimiento,
              fechaIngreso: user.fechaIngreso,
              fotoBase64: user.foto,
            });
            localStorage.setItem(this.USER_KEY, JSON.stringify(user));
          }
        },
      });
    } else {
      const storedUser =
        localStorage.getItem(this.USER_KEY) ||
        sessionStorage.getItem(this.USER_KEY);
      if (storedUser) {
        this.currentUserSubject.next(JSON.parse(storedUser));
      }
    }
  }

  logout(): Observable<void> {
    const url = `${environment.api}${this.endpoint}${'/logout'}`;
    return this.http.post<void>(url, null).pipe(
      tap(() => {
        this.clearSession();
      })
    );
  }

  clearSession(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    sessionStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    sessionStorage.removeItem(this.USER_KEY);
    this.isAuthenticatedSubject.next(false);
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return (
      localStorage.getItem(this.TOKEN_KEY) ||
      sessionStorage.getItem(this.TOKEN_KEY)
    );
  }

  hasToken(): boolean {
    const token = this.getToken();
    return token !== null && token !== '';
  }

  saveToken(token: string, recordar: boolean = false): void {
    if (recordar) {
      localStorage.setItem(this.TOKEN_KEY, token); // Persistente
    } else {
      sessionStorage.setItem(this.TOKEN_KEY, token); // Temporal
    }
  }
}
