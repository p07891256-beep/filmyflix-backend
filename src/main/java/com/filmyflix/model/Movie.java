package com.filmyflix.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movies")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tmdbId;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String genre;
    private Integer releaseYear;
    private String posterUrl;
    private String trailerUrl;
    private Double rating;
}
