package com.aronreis.movie;

import java.util.List;
import java.util.Optional;

public interface MovieDao {
    List<Movie> selectMovies();
    int insertMovie(Movie movie);
    int deleteMovie(int id);
    Optional<Movie> selectMovieById(int id);
    Optional<Movie> selectMovieByName(String name);
    int updateMovie(Movie movie, int id);
}
