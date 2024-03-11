import axios from "axios";

const REST_API_BASE_URL= 'http://localhost:8080/api/movies';

const REST_API_BASE_URL_SHOW= 'http://localhost:8080/api/showtimes';

export const listShowTimes = (selectedGenre) => {
    const url = selectedGenre ? `${REST_API_BASE_URL}?genre=${selectedGenre}` : REST_API_BASE_URL;
    return axios.get(url);
};

export const getShowTime = (showId) => axios.get(REST_API_BASE_URL_SHOW + '/' + showId);