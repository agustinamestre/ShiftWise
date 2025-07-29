import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConceptResponse } from '../interface/ConceptResponse';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConceptService {
  endpoint: string = 'conceptos' ;
  constructor(private http: HttpClient) { }

  getConcepts(): Observable<ConceptResponse[]> {
    let url = environment.api + this.endpoint;
    return this.http.get<ConceptResponse[]>(url);
  }
}
