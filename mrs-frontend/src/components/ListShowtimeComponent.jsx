import React, { useEffect, useState } from 'react';
import { listShowTimes } from '../services/ShowTimeService';
import { getCustomer } from '../services/CustomerService';
import { listRatings } from '../services/RatingService';

import { Link } from 'react-router-dom'

const ListMovieComponent = () => {
    const [movies, setMovies] = useState([]);
    const [selectedGenre, setSelectedGenre] = useState('');
    const [customer, SetCustomer] = useState([]);
    const [ratings, SetRatings] = useState([]);
    const [ageFilter, setAgeFilter] = useState('');
    const [languageFilter, setLanguageFilter] = useState('');

    useEffect(() => {
        const fetchShowTimes = async () => {
            try {
                const response = await listShowTimes(selectedGenre, ageFilter,languageFilter);
                setMovies(response.data);
            } catch (error) {
                console.error(error);
            }
        };
    
        fetchShowTimes();
    }, [selectedGenre, ageFilter,languageFilter]);

    const handleLanguageChange = (event) => {
        setLanguageFilter(event.target.value);
    };

    const handleGenreChange = (event) => {
        setSelectedGenre(event.target.value);
    };

    const handleAgeFilter = (event) => {
        setAgeFilter(event.target.value);
    };

    useEffect(() => {
        const fetchRatings = async() => {
            try {
                const response = await listRatings(localStorage.getItem("customerId"))
                console.log(response.data)
                SetRatings(response.data);
            }
            catch(error) {
                console.log(error);
            }
        };
    
        fetchRatings();
    }, []);

    useEffect(() => {
        const fetchCustomer = async() => {
            try {
                const response = await getCustomer(localStorage.getItem("customerId"))
                console.log(response.data)
                SetCustomer(response.data);
            }
            catch(error) {
                console.log(error);
            }
        };
    
        fetchCustomer();
    }, []);

    return (
        <div className='container'>
            <h2 className='text-center'>List of Movies</h2>
            <div className='genre-dropdown'>
                <label htmlFor='genre'>Select Genre:</label>
                <select id='genre' onChange={handleGenreChange} value={selectedGenre}>
                    <option value=''>All Genres</option>
                    <option value='Action'>Action</option>
                    <option value='Comedy'>Comedy</option>
                    <option value='Drama'>Drama</option>
                </select>
            </div>
            <div className='age-filter'>
                <label htmlFor='age'>Select Age:</label>
                <select id='age' onChange={handleAgeFilter} value={ageFilter}>
                    <option value=''>All ages</option>
                    <option value='12'>12</option>
                    <option value='14'>14</option>
                    <option value='16'>16</option>
                </select>
            </div>
            <div className='lang-filter'>
                <label htmlFor='language'>Select Language:</label>
                <select id='language' onChange={handleLanguageChange} value={languageFilter}>
                    <option value=''>All languages</option>
                    <option value='Estonian'>Estonian</option>
                    <option value='English'>English</option>
                </select>
            </div>
            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        <th>Showtime Id</th>
                        <th>Movie Id</th>
                        <th>Movie Name</th>
                        <th>Genre</th>
                        <th>Language</th>
                        <th>Minimum age</th>
                        <th>Start time</th>
                        <th>End time</th>
                        <th>Screen</th>
                        <th></th> 
                    </tr>
                </thead>
                <tbody>
                    {movies.map(movie =>
                        movie.showtimes.map(time =>
                            <tr key={time.id}>
                                <td>{time.id}</td>
                                <td>{movie.id}</td>
                                <td>{movie.name}</td>
                                <td>{movie.genre}</td>
                                <td>{movie.language}</td>
                                <td>{movie.minimumAge}</td>
                                <td>{time.startTime}</td>
                                <td>{time.endTime}</td>
                                <td>{time.screen.id}</td>
                                <td>
                                    <Link to={`/showtimes/${time.id}`} className="btn btn-primary">Osta piletid</Link>
                                </td> 
                            </tr>
                        )
                    )}
                </tbody>
            </table>
            {ratings.length > 0 && (
                <Link to={`/movies/${localStorage.getItem("customerId")}`}className="btn btn-secondary"> Soovita filme vaatamisajaloo põhjal.</Link> 
            )}
            <p></p>
        </div>
    );
};

export default ListMovieComponent;
