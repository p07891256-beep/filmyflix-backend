package com.filmyflix.controller;

import com.filmyflix.model.Movie;
import com.filmyflix.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Movie> getAllMovies(@RequestParam(required = false) String genre) {
        if (genre != null && !genre.isEmpty()) {
            return movieRepository.findByGenreContainingIgnoreCase(genre);
        }
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @GetMapping("/search")
    public List<Movie> search(@RequestParam String q) {
        return movieRepository.findByTitleContainingIgnoreCase(q);
    }

    @GetMapping("/{id}/recommendations")
    public List<Movie> recommendations(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) return List.of();
        return movieRepository.findByGenreContainingIgnoreCaseAndIdNot(movie.getGenre(), id);
    }
}
