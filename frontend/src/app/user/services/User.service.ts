import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import UserRequest from "../interfaces/UserRequest";
import { Observable } from "rxjs";
import { UserResponse } from "../interfaces/UserResponse";
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: 'root',
})
export class UserService {
  endpoint: string = 'users';
  constructor(private http: HttpClient) {}

  createUser(request: UserRequest): Observable<UserResponse> {
    let url = environment.api + this.endpoint;

    return this.http.post<UserResponse>(url, request);
  }

  getUserById(id: string): Observable<UserResponse> {
    let url = `${environment.api}${this.endpoint}/${id}`;
    return this.http.get<UserResponse>(url);
  }

  getUsers(): Observable<UserResponse[]> {
    let url = environment.api + this.endpoint;
    return this.http.get<UserResponse[]>(url);
  }
}
