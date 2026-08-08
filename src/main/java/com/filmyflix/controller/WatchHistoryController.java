package com.filmyflix.controller;

import com.filmyflix.model.Movie;
import com.filmyflix.model.Profile;
import com.filmyflix.model.WatchHistory;
import com.filmyflix.repository.MovieRepository;
import com.filmyflix.repository.ProfileRepository;
import com.filmyflix.repository.WatchHistoryRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/watch-history")
public class WatchHistoryController {

    private final WatchHistoryRepository watchHistoryRepository;
    private final ProfileRepository profileRepository;
    private final MovieRepository movieRepository;

    public WatchHistoryController(WatchHistoryRepository watchHistoryRepository,
                                   ProfileRepository profileRepository,
                                   MovieRepository movieRepository) {
        this.watchHistoryRepository = watchHistoryRepository;
        this.profileRepository = profileRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<WatchHistory> getHistory(@RequestParam Long profileId) {
        return watchHistoryRepository.findByProfileIdOrderByLastWatchedAtDesc(profileId);
    }

    @PostMapping
    public WatchHistory updateProgress(@RequestParam Long profileId,
                                        @RequestParam Long movieId,
                                        @RequestParam Integer progressPercent) {
        WatchHistory entry = watchHistoryRepository.findByProfileIdAndMovieId(profileId, movieId)
                .orElseGet(() -> {
                    WatchHistory wh = new WatchHistory();
                    Profile profile = profileRepository.findById(profileId).orElseThrow();
                    Movie movie = movieRepository.findById(movieId).orElseThrow();
                    wh.setProfile(profile);
                    wh.setMovie(movie);
                    return wh;
                });

        entry.setProgressPercent(progressPercent);
        entry.setLastWatchedAt(LocalDateTime.now());
        return watchHistoryRepository.save(entry);
    }
}
