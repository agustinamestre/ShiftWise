import { HttpClient, HttpErrorResponse, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { JornadaRequest } from "../interface/JornadaRequest";
import { environment } from "../../../../environments/environment";
import { JornadaResponse } from '../interface/JornadaResponse';
import ErrorResponse from "../../../models/ErrorResponse";

@Injectable({
  providedIn: 'root',
})
export class JornadaService {
  private endpoint = 'jornadas';

  constructor(private http: HttpClient) {}

  createJornada(jornada: JornadaRequest): Observable<JornadaResponse> {
    const url = `${environment.api}${this.endpoint}`;
    return this.http.post<JornadaResponse>(url, jornada)
    .pipe(catchError(this.handleError));
  }

  getJornadas(nroDocumento?: string, fecha?: string, apellido?: string): Observable<JornadaResponse[]> {
    const url = `${environment.api}${this.endpoint}`;

    let params = new HttpParams();

    if (nroDocumento) {
      params = params.set('nroDocumento', nroDocumento);
    }
    if (apellido) {
      params = params.set('apellido', apellido);
    }
    if (fecha) {
      params = params.set('fecha', fecha);
    }
    return this.http.get<JornadaResponse[]>(url, { params });
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
