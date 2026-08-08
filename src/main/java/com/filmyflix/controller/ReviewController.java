package com.filmyflix.controller;

import com.filmyflix.model.Movie;
import com.filmyflix.model.Profile;
import com.filmyflix.model.Review;
import com.filmyflix.repository.MovieRepository;
import com.filmyflix.repository.ProfileRepository;
import com.filmyflix.repository.ReviewRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ProfileRepository profileRepository;
    private final MovieRepository movieRepository;

    public ReviewController(ReviewRepository reviewRepository,
                             ProfileRepository profileRepository,
                             MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.profileRepository = profileRepository;
        this.movieRepository = movieRepository;
    }

    @PostMapping("/reviews")
    public Review addReview(@RequestParam Long profileId,
                             @RequestParam Long movieId,
                             @RequestParam Integer rating,
                             @RequestParam(required = false) String comment) {
        Profile profile = profileRepository.findById(profileId).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();

        Review review = new Review();
        review.setProfile(profile);
        review.setMovie(movie);
        review.setRating(rating);
        review.setComment(comment);
        return reviewRepository.save(review);
    }

    @GetMapping("/movies/{movieId}/reviews")
    public List<Review> getReviews(@PathVariable Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }
}
