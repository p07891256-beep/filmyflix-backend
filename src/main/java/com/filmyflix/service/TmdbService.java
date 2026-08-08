package com.filmyflix.service;

import com.filmyflix.model.Movie;
import com.filmyflix.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.base.url}")
    private String baseUrl;

    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public TmdbService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @SuppressWarnings("unchecked")
    public List<Movie> syncPopularMovies() {
        String url = baseUrl + "/movie/popular?api_key=" + apiKey + "&language=en-US&page=1";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

        List<Movie> savedMovies = new ArrayList<>();

        for (Map<String, Object> item : results) {
            Long tmdbId = Long.valueOf(item.get("id").toString());

            if (movieRepository.findAll().stream().anyMatch(m -> tmdbId.equals(m.getTmdbId()))) {
                continue;
            }

            Movie movie = new Movie();
            movie.setTmdbId(tmdbId);
            movie.setTitle((String) item.get("title"));
            movie.setDescription((String) item.get("overview"));

            Object genreIds = item.get("genre_ids");
            movie.setGenre(genreIds != null ? genreIds.toString() : "Unknown");

            String releaseDate = (String) item.get("release_date");
            if (releaseDate != null && releaseDate.length() >= 4) {
                movie.setReleaseYear(Integer.parseInt(releaseDate.substring(0, 4)));
            }

            String posterPath = (String) item.get("poster_path");
            movie.setPosterUrl(posterPath != null ? "https://image.tmdb.org/t/p/w500" + posterPath : null);

            Object rating = item.get("vote_average");
            movie.setRating(rating != null ? Double.valueOf(rating.toString()) : 0.0);

            savedMovies.add(movieRepository.save(movie));
        }

        return savedMovies;
    }
}