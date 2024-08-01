import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {FilesAndDirectoriesResponse} from "src/app/Models/directory.model";

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  private apiUrl = 'http://localhost:8080/bfi/v1'; // Update to your backend URL

  constructor(private http: HttpClient) {}

  upload(file: File, id: number): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    formData.append('id',id.toString());

    return this.http.post(`${this.apiUrl}/files/upload`, formData);
  }

  getAllFilesAndDirectories(): Observable<FilesAndDirectoriesResponse> {
    return this.http.get<FilesAndDirectoriesResponse>(`${this.apiUrl}/files/all`);
  }
  deleteFile(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/files/${id}`);
  }
}
