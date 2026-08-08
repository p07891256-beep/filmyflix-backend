package com.filmyflix.controller;

import com.filmyflix.model.Movie;
import com.filmyflix.model.Profile;
import com.filmyflix.model.Watchlist;
import com.filmyflix.repository.MovieRepository;
import com.filmyflix.repository.ProfileRepository;
import com.filmyflix.repository.WatchlistRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    private final WatchlistRepository watchlistRepository;
    private final ProfileRepository profileRepository;
    private final MovieRepository movieRepository;

    public WatchlistController(WatchlistRepository watchlistRepository,
                                ProfileRepository profileRepository,
                                MovieRepository movieRepository) {
        this.watchlistRepository = watchlistRepository;
        this.profileRepository = profileRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Watchlist> getWatchlist(@RequestParam Long profileId) {
        return watchlistRepository.findByProfileId(profileId);
    }

    @PostMapping
    public Watchlist addToWatchlist(@RequestParam Long profileId, @RequestParam Long movieId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();

        Watchlist entry = new Watchlist();
        entry.setProfile(profile);
        entry.setMovie(movie);
        return watchlistRepository.save(entry);
    }

    @DeleteMapping("/{movieId}")
    public void removeFromWatchlist(@PathVariable Long movieId, @RequestParam Long profileId) {
        watchlistRepository.findByProfileIdAndMovieId(profileId, movieId)
                .ifPresent(watchlistRepository::delete);
    }
}
