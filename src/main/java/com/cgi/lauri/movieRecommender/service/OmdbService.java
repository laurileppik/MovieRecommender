package com.cgi.lauri.movieRecommender.service;

import com.cgi.lauri.movieRecommender.model.OmdbResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OmdbService {

    @Value("${omdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public OmdbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchImdbRating(String title) {
        String url = buildOmdbApiUrl(title);
        OmdbResponse response = restTemplate.getForObject(url, OmdbResponse.class);
        if (response != null && response.getImdbRating() != null) {
            return response.getImdbRating();
        }
        return "N/A";
    }

    public String fetchGenre(String title) {
        String url = buildOmdbApiUrl(title);
        OmdbResponse response = restTemplate.getForObject(url, OmdbResponse.class);
        if (response != null && response.getGenre() != null) {
            String[] splitGenre = response.getGenre().split(", ");
            return splitGenre[0];
        }
        return "N/A";
    }
    public String fetchLanguage(String title) {
        String url = buildOmdbApiUrl(title);
        OmdbResponse response = restTemplate.getForObject(url, OmdbResponse.class);
        if (response != null && response.getLanguage() != null) {
            String[] splitLanguage = response.getLanguage().split(", ");
            return (!splitLanguage[0].equals("None")) ? splitLanguage[0] : splitLanguage[1];
        }
        return "N/A";
    }

    private String buildOmdbApiUrl(String title) {
        return UriComponentsBuilder.fromHttpUrl("http://www.omdbapi.com/")
                .queryParam("t", title)
                .queryParam("apikey", apiKey)
                .toUriString().replace("%20", "+");
    }
}