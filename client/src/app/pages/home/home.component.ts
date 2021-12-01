import { Component, OnInit } from '@angular/core';
import { take } from 'rxjs/operators';
import { FilmsService } from './films.service';
import { Review } from './home-review.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  loadedReviews: Review;
  isLoading = false;

  constructor(private filmsService: FilmsService) {}

  ngOnInit(): void {
    this.fetchFilms();
  }

  private fetchFilms(): void {
    this.isLoading = true;
    this.filmsService
      .fetchFilms()
      .pipe(take(1))
      .subscribe((reviews) => {
        console.log(
          '🚀 ~ file: home.component.ts ~ line 28 ~ HomeComponent ~ .subscribe ~ reviews',
          reviews
        );
        // this.loadedReviews = reviews;
        this.isLoading = false;
      });
  }
}
