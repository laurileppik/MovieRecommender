import axios from "axios";

const headers = {
    Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
};

const REST_API_BASE_URL= 'http://localhost:8080/api/movies';

const REST_API_BASE_URL_SHOW= 'http://localhost:8080/api/showtimes';

export const listShowTimes = (selectedGenre, ageFilter, languageFilter, dateFilter, movieId) => {
  let url = REST_API_BASE_URL;
  const params = new URLSearchParams();
  if (selectedGenre) {
    params.append("genre", selectedGenre);
  }
  if (ageFilter) {
    params.append("minAge", ageFilter);
  }
  if (languageFilter) {
    params.append("language",languageFilter);
  }
  if (dateFilter) {
    params.append("date",dateFilter);
  }
  if (movieId) {
    params.append("movieId",movieId);
  }
  url += `?${params.toString()}`;
  return axios.get(url, { headers });
};

export const getShowTime = (showId) => axios.get(REST_API_BASE_URL_SHOW + '/' + showId, { headers });

export const setShowTime = (show) => axios.post(REST_API_BASE_URL_SHOW, show, {headers});