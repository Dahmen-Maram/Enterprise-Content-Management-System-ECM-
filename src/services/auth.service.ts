import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = `${environment.apiUrl}/bfi/v1/auth/login`;

  constructor(private http: HttpClient) { }

  signIn(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post<any>(this.apiUrl, body);
  }
  private _isAuthenticated = new BehaviorSubject<boolean>(false);
  // Observable to expose authentication status
  isAuthenticated$ = this._isAuthenticated.asObservable();

  // Method to check authentication status
  get isAuthenticated(): boolean {
    return this._isAuthenticated.getValue();
  }

  // Method to log in and update authentication status
  login(): void {
    this._isAuthenticated.next(true);
  }

  // Method to log out and update authentication status
  logout(): void {
    this._isAuthenticated.next(false);
  }
}
