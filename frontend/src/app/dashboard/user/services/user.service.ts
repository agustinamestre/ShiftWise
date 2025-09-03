import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import UserRequest from "../interfaces/UserRequest";
import { catchError, Observable, throwError } from "rxjs";
import { UserResponse } from "../interfaces/UserResponse";
import { environment } from "../../../environments/environment";
import ErrorResponse from "../../models/ErrorResponse";

@Injectable({
  providedIn: 'root',
})
export class UserService {
  endpoint: string = 'users';
  constructor(private http: HttpClient) {}

  createUser(request: UserRequest): Observable<UserResponse> {
    let url = environment.api + this.endpoint;

    return this.http
      .post<UserResponse>(url, request)
      .pipe(catchError(this.handleError));
  }

  getUserById(id: string): Observable<UserResponse> {
    let url = `${environment.api}${this.endpoint}/${id}`;
    return this.http.get<UserResponse>(url);
  }

  getUsers(): Observable<UserResponse[]> {
    let url = environment.api + this.endpoint;
    return this.http.get<UserResponse[]>(url);
  }

  updateUser(id: string, request: UserRequest): Observable<UserResponse> {
    let url = environment.api + this.endpoint + '/' + id;

    return this.http
      .put<UserResponse>(url, request)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let backendErrors: any[] = [];

    if (error.error && Array.isArray(error.error.error)) {
      backendErrors = error.error.error;
    }

    const errorResponse: ErrorResponse = {
      genericErrorMessage:
        backendErrors.length > 0
          ? backendErrors[0].errorMessage
          : 'Ocurrió un error inesperado',
      statusCode: error.status,
      propertyErrors: backendErrors.map((e: any) => ({
        property: e.errorCode
          ?.replace('_invalido', '')
          .replace('_requerido', ''),
        error: e.errorMessage,
      })),
    };

    return throwError(() => errorResponse);
  }
}
