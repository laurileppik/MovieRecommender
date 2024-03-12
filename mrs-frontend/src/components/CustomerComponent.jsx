import React, { useEffect, useState } from 'react';
import { getCustomer } from '../services/CustomerService';
import { useParams } from 'react-router-dom';

const CustomerComponent = () => {
    const { customerID } = useParams();
    const [customer, SetCustomer] = useState('');

    useEffect(() => {
        const fetchCustomer = async() => {
            try {
                const response = await getCustomer(customerID)
                SetCustomer(response.data);
            }
            catch(error) {
                console.log(error);
            }
        };

        fetchCustomer();
    }, [customerID]);

    return (
      <div>
          <p>ID: {customer.id}</p>
          <p>Name: {customer.firstName}</p>
      </div>
    )
  }

export default CustomerComponent;