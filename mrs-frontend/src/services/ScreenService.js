import axios from "axios";

const REST_API_BASE_URL= 'http://localhost:8080/api/screens'

export const getScreen = (screenId) => axios.get(REST_API_BASE_URL + '/' + screenId);