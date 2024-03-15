import React from 'react';
import { useNavigate } from 'react-router-dom';

import { Link } from 'react-router-dom'

const HeaderComponent = () => {
    const history = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('customerId');
        console.log("Logout successful")
        history('/'); 
    };

    return (
        <div>
            <header>
                <nav className="navbar navbar-expand-lg navbar-light bg-light">
                    <a className="navbar-brand" href="/">Apollo cinema</a>
                    <button className="btn btn-link" onClick={handleLogout}> Logout </button>
                    {!(localStorage.getItem("customerId")) && (
                <Link to={`http://localhost:3000/login`} className="btn btn-primary">Login</Link>
            )} 
                </nav>
            </header>
        </div>
    );
};

export default HeaderComponent;
