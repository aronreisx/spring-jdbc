package com.aronreis.movie;

import com.aronreis.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieDao movieDao;

    public MovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public List<Movie> getMovies() {
        return movieDao.selectMovies();
    }

    public void addNewMovie(Movie movie) {
        Optional<Movie> existentMovie = movieDao.selectMovieByName(movie.name());
        existentMovie.ifPresentOrElse(item -> {
            throw new NotFoundException(String.format("A movie with the name \"%s\" already exists.", item.name()));
        }, () -> {
            int result = movieDao.insertMovie(movie);
            if (result != 1) {
                throw new IllegalStateException("The movie couldn't been added.");
            }
        });
    }

    public void deleteMovie(Integer id) {
        Optional<Movie> movies = movieDao.selectMovieById(id);
        movies.ifPresentOrElse(movie -> {
            int result = movieDao.deleteMovie(id);
            if (result != 1) {
                throw new IllegalStateException("The movie couldn't been deleted.");
            }
        }, () -> {
            throw new NotFoundException(String.format("The movie with id \"%s\" wasn't not found.", id));
        });
    }

    public Movie getMovie(int id) {
        return movieDao.selectMovieById(id)
            .orElseThrow(() -> new NotFoundException(String.format("The movie with id \"%s\" wasn't found.", id)));
    }

    public void updateMovie(Movie movie, Integer id) {
        Optional<Movie> existentMovie = movieDao.selectMovieById(id);
        existentMovie.ifPresentOrElse(item -> {
            int result = movieDao.deleteMovie(id);
            if (result != 1) {
                throw new IllegalStateException("Something went wrong. The movie couldn't been updated.");
            }
        }, () -> {
            throw new NotFoundException(String.format("The movie with id \"%s\" wasn't found", id));
        });

        int result = movieDao.updateMovie(movie, id);
        if (result != 1) {
            throw new IllegalStateException("Something went wrong");
        }
    }
}
