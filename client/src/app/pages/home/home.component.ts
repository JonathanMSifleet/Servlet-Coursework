import { Component, OnInit } from '@angular/core';
import { take } from 'rxjs/operators';
import { Review } from './home-review.model';
import { ReviewsService } from './reviews.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  loadedReviews: Review;
  isLoading = false;

  constructor(private reviewsService: ReviewsService) {}

  ngOnInit(): void {
    this.fetchReviews();
  }

  private fetchReviews(): void {
    this.isLoading = true;
    this.reviewsService
      .fetchReviews()
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
