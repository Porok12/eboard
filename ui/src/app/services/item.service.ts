import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError, retry} from "rxjs/operators";
import {Item} from "../models/item.model";
import {Category} from "../models/category.model";

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private apiURL = '/api/items';

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  getAll(): Observable<Item> {
    return this.http.get<Item>(this.apiURL)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  getOne(id: string): Observable<Item> {
    return this.http.get<Item>([this.apiURL, id].join('/'))
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  create(item: Item): Observable<Item>  {
    return this.http.post<Item>(this.apiURL, JSON.stringify(item), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  createWithin(category: Category, item: Item): Observable<Item>  {
    // @ts-ignore
    return this.http.post<Item>(`/api/categories/${category._id.$oid}/item`, JSON.stringify(item), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  update(id: string, todo: Item): Observable<Item> {
    return this.http.put<Item>(`${this.apiURL}/${id}`, JSON.stringify(todo), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  delete(todoItem: string | Item): Observable<any> {
    const id = typeof todoItem === 'string' ? todoItem : todoItem._id;
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
