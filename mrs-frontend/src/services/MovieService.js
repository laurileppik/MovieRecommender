import axios from "axios";

const headers = {
    Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
  };

const REST_API_BASE_URL= 'http://localhost:8080/api/movies'

export const listMovies = (customerId) => {
    return axios.get(REST_API_BASE_URL+ '/sorted/' + customerId, {headers})
}

export const getMovie = (movieId) => axios.get(REST_API_BASE_URL + '/' + movieId, {headers});

export const setMovie = (movie) => {
  return axios.post(REST_API_BASE_URL, movie, { headers });
};

export const getGenres = () => {
  return axios.get(REST_API_BASE_URL + '/genres', {headers})
};

export const getAges = () => {
  return axios.get(REST_API_BASE_URL + '/ages', {headers})
}

export const getLanguages = () => {
  return axios.get(REST_API_BASE_URL + '/languages', {headers})
}