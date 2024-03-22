import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/App.css'

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
                <nav className="navbar navbar-expand-lg navbar-black bg-black">
                    <a className="navbar-brand" href="/" style={{ color: 'white' }}>Budget apollo</a>
                    <button className="btn btn-link" onClick={handleLogout}> Logi välja </button>
                    {!(localStorage.getItem("customerId")) && (
                        <Link to={`http://localhost:3000/login`} className="btn btn-primary">Logi sisse</Link>
            )} 
                </nav>
            </header>
        </div>
    );
};

export default HeaderComponent;
