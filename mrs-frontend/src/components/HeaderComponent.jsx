import React from 'react';
import { useNavigate } from 'react-router-dom';

const HeaderComponent = () => {
    const history = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('jwtToken');
        console.log("Logout successful")
        history('/'); 
    };

    return (
        <div>
            <header>
                <nav className="navbar navbar-expand-lg navbar-light bg-light">
                    <a className="navbar-brand" href="/">Apollo cinema</a>
                    <button className="btn btn-link" onClick={handleLogout}> Logout </button>
                </nav>
            </header>
        </div>
    );
};

export default HeaderComponent;
