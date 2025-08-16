import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { LoginRequest } from "../interfaces/LoginRequest";
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

  login(credentials: LoginRequest): Observable<any> {
    let url = `${environment.api}${this.endpoint}`;

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
        }
      })
    );
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
