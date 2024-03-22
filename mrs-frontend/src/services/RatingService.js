import axios from "axios";

const headers = {
    Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
};

const REST_API_BASE_URL= 'http://localhost:8080/user/ratings'

export const listRatings = (customerId) => {
    return axios.get(REST_API_BASE_URL + '/' + customerId, {headers})
}

export const setMovieRating = (movieRating) => {
  console.log(movieRating)
  return axios.post(REST_API_BASE_URL,movieRating, {headers})
}