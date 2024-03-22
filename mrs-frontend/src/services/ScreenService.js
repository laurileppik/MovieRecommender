import axios from "axios";

const headers = {
    Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
};

const REST_API_BASE_URL= 'http://localhost:8080/api/screens'

export const getScreen = (screenId, ticketCount) => axios.get(REST_API_BASE_URL + '/' + screenId , {
    params: {
      ticketCount: ticketCount
  },headers
});