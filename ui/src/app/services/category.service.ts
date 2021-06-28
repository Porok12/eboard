import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError, retry} from "rxjs/operators";
import {Category} from "../models/category.model";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiURL = '/api/categories';

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  getAll(): Observable<Category> {
    return this.http.get<Category>(this.apiURL).pipe(
      retry(1),
      catchError(this.handleError)
    )
  }

  create(category: Category): Observable<any> {
    return this.http.post<any>(this.apiURL, JSON.stringify(category), this.httpOptions).pipe(
      retry(1),
      catchError(this.handleError)
    )
  }

  delete(category: string | Category): Observable<any> {
    const id = typeof category === 'string' ? category : category._id;
    // @ts-ignore
    return this.http.delete<any>(`${this.apiURL}/${id.$oid}`, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  handleError(error) {
    let errorMessage = '';
    if(error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
