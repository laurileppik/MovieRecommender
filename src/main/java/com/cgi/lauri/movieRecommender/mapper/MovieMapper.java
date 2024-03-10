package com.cgi.lauri.movieRecommender.mapper;

import com.cgi.lauri.movieRecommender.dto.MovieDto;
import com.cgi.lauri.movieRecommender.model.Movie;

public class MovieMapper {
    public static MovieDto mapToMovieDTO(Movie movie) {
        return new MovieDto(
                movie.getId(), movie.getName(), movie.getGenre(), movie.getLanguage(), movie.getShowtimes(), movie.getMinimumAge()
        );
    }

    public static Movie maptoMovie(MovieDto movieDto) {
        return new Movie(
                movieDto.getId(), movieDto.getName(), movieDto.getGenre(), movieDto.getLanguage(), movieDto.getShowtimes(), movieDto.getMinimumAge()
        );
    }
}
