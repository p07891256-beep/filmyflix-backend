package com.filmyflix.controller;

import com.filmyflix.model.Movie;
import com.filmyflix.service.TmdbService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final TmdbService tmdbService;

    public AdminController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @PostMapping("/sync-movies")
    public List<Movie> syncMovies() {
        return tmdbService.syncPopularMovies();
    }
}