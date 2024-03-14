import axios from "axios";

const headers = {
    Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
  };


const REST_API_BASE_URL= 'http://localhost:8080/api/customers'

export const getCustomer = (customerId) => axios.get(REST_API_BASE_URL + "/" + customerId, {headers});