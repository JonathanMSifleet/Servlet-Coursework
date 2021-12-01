import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { from, map } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({ providedIn: 'root' })
export class FilmsService {
  // @ts-expect-error
  constructor(private http: HttpClient) {}

  fetchFilms(): Observable<any> {
    return from(
      fetch('http://127.0.0.1:8080/ServletCoursework/getAllFilms', {
        method: 'GET',
        headers: { 'Access-Control-Allow-Origin': '*' }
      })
    ).pipe(
      map((responseData) => {
        return responseData;
      })
    );
  }
}
