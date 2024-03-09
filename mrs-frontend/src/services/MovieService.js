import axios from "axios";

const REST_API_BASE_URL= 'http://localhost:8080/api/movies'

export const listMovies = () => {
    return axios.get(REST_API_BASE_URL)
}

