import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { LoginRequest } from "../interfaces/LoginRequest";
import { LoginResponse } from "../interfaces/LoginResponse";
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  endpoint: string = 'auth/login';
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY = 'current_user';

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(
    this.hasToken()
  );
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    let url = environment.api + this.endpoint;

    return this.http.post<LoginResponse>(url, credentials).pipe(
      tap((response) => {
        // Guardar token y usuario en localStorage
        localStorage.setItem(this.TOKEN_KEY, response.token);
        localStorage.setItem(this.USER_KEY, response.nroDocumento);
        this.isAuthenticatedSubject.next(true);
      })
    );
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  private hasToken(): boolean {
    const token = this.getToken();
    return token !== null && token !== '';
  }
}
