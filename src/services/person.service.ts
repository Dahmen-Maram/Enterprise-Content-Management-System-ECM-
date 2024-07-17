import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Person } from '../app/Models/Person.model';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  private apiUrl = `${environment.apiUrl}/bfi/v1/person/saveperson`;
  constructor(private http: HttpClient) {
  }

  addPerson(person: Person): Observable<Person> {
    return this.http.post<Person>(this.apiUrl, person);
  }
}
