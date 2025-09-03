import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { JornadaRequest } from "../interface/JornadaRequest";
import { environment } from "../../../../environments/environment";
import Jornada from "../interface/Jornada";

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
}
