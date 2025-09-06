import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { JornadaRequest } from "../interface/JornadaRequest";
import { environment } from "../../../../environments/environment";
import Jornada from "../interface/Jornada";
import { JornadaResponse } from '../interface/JornadaResponse';

@Injectable({
  providedIn: 'root',
})
export class JornadaService {
  private endpoint = 'jornadas';

  constructor(private http: HttpClient) {}

  createJornada(jornada: JornadaRequest): Observable<Jornada> {
    const url = `${environment.api}${this.endpoint}`;
    return this.http.post<Jornada>(url, jornada);
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
}
