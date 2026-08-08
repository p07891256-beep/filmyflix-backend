package com.filmyflix.repository;

import com.filmyflix.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByProfileId(Long profileId);
    Optional<Watchlist> findByProfileIdAndMovieId(Long profileId, Long movieId);
}
