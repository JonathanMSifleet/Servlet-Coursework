import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/operators';
import { Review } from './home-review.model';

@Injectable({ providedIn: 'root' })
export class ReviewsService {
  constructor(private http: HttpClient) {}

  fetchReviews(): Observable<Review> {
    return this.http
      .get<{ [key: string]: Review }>(
        'http://127.0.0.1:8080/ServletTemplate/getAllFilms'
        // {
        //   headers: { 'Access-Control-Allow-Origin': '*' }
        // }
      )
      .pipe(
        map((responseData) => {
          return responseData.reviews;
        })
      );
  }
  //   return this.http
  //     .get<{ [key: string]: Review }>(
  //       'http://127.0.0.1:8080/ServletTemplate/getAllFilms', {
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
