import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { from, map } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({ providedIn: 'root' })
export class ReviewsService {
  // @ts-expect-error
  constructor(private http: HttpClient) {}

  fetchReviews(): Observable<Response> {
    return from(
      fetch('http://localhost:8080/ServletCoursework/getAllFilms', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        },
        mode: 'no-cors' // the most important option
      })
    ).pipe(
      map((responseData) => {
        return responseData;
        // return responseData.reviews;
      })
    );
  }

  //   return this.http
  //     .get<{ [key: string]: Review }>(
  //       'http://127.0.0.1:8080/ServletCoursework/getAllFilms', {
  //         headers: {'Access-Control-Allow-Origin': '*'}
  //       }
  //     )
  //     .pipe(
  //       map((responseData) => {
  //         return responseData.reviews;
  //       })
  //     );
  // }
}
