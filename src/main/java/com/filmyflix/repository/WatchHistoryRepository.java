package com.filmyflix.repository;

import com.filmyflix.model.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {
    List<WatchHistory> findByProfileIdOrderByLastWatchedAtDesc(Long profileId);
    Optional<WatchHistory> findByProfileIdAndMovieId(Long profileId, Long movieId);
}
