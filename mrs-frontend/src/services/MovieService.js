import axios from "axios";

const headers = {
    Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
  };

const REST_API_BASE_URL= 'http://localhost:8080/api/movies'

export const listMovies = () => {
    return axios.get(REST_API_BASE_URL)
}

export const getMovie = (movieId) => axios.get(REST_API_BASE_URL + '/' + movieId, {headers});