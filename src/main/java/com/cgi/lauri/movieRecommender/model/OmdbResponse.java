package com.cgi.lauri.movieRecommender.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OmdbResponse {
    @JsonProperty("imdbRating")
    private String imdbRating;
    @JsonProperty("Genre")
    private String genre;
    @JsonProperty("Language")
    private String language;
}
