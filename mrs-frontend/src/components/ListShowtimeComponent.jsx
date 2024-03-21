import React, { useEffect, useState } from 'react';
import { listShowTimes } from '../services/ShowTimeService';
import { getCustomer } from '../services/CustomerService';
import { listRatings } from '../services/RatingService';
import { useParams } from 'react-router-dom';

import { Link } from 'react-router-dom'
import { getGenres,getAges,getLanguages } from '../services/MovieService';

const ListShowtimeComponent = () => {
    const [movies, setMovies] = useState([]);
    const [selectedGenre, setSelectedGenre] = useState('');
    const [customer, SetCustomer] = useState([]);
    const [ratings, SetRatings] = useState([]);
    const [ageFilter, setAgeFilter] = useState('');
    const [languageFilter, setLanguageFilter] = useState('');
    const [date, setDayFilter] = useState('');
    const [genres, setGenres] = useState([]);
    const [ages, setAges] = useState([]);
    const [languages, setLanguages] = useState([]);
    const { filterMovieId } = useParams();
    

    useEffect(() => {
        const fetchShowTimes = async () => {
            try {
                const response = await listShowTimes(selectedGenre, ageFilter,languageFilter,date,filterMovieId);
                setMovies(response.data);
            } catch (error) {
                console.error(error);
            }
        };
    
        fetchShowTimes();
    }, [selectedGenre, ageFilter,languageFilter,date,filterMovieId]);

    useEffect(() => {
        const fetchGenres = async () => {
            try {
                const response = await getGenres();
                setGenres(response.data);
            } catch (error) {
                console.error(error);
            }
        };
        
        fetchGenres();
    }, []);

    useEffect(() => {
        const fetchAges = async () => {
            try {
                const response = await getAges();
                setAges(response.data);
            } catch (error) {
                console.error(error);
            }
        };
        
        fetchAges();
    }, []);

    useEffect(() => {
        const fetchLanguages = async () => {
            try {
                const response = await getLanguages();
                setLanguages(response.data);
            } catch (error) {
                console.error(error);
            }
        };
        
        fetchLanguages();
    }, []);

    const handleLanguageChange = (event) => {
        setLanguageFilter(event.target.value);
    };

    const handleGenreChange = (event) => {
        setSelectedGenre(event.target.value);
    };

    const handleAgeFilter = (event) => {
        setAgeFilter(event.target.value);
    };

    const handleDayFilter = (selectedDate) => {
        setDayFilter(selectedDate);
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
                    {genres.map(genre => (
                        <option key={genre} value={genre}>{genre}</option>
                    ))}
                </select>
            </div>
            <div className='age-filter'>
                <label htmlFor='age'>Select Age:</label>
                <select id='age' onChange={handleAgeFilter} value={ageFilter}>
                <option value=''>All ages</option>
                    {ages.map(age => (
                        <option key = {age} value = {age}>{age}</option>
                    ))}
                </select>
            </div>
            <div className='lang-filter'>
                <label htmlFor='language'>Select Language:</label>
                <select id='language' onChange={handleLanguageChange} value={languageFilter}>
                    <option value=''>All languages</option>
                    {languages.map(language => (
                        <option key = {language} value={language}>{language}</option>
                    ))}
                </select>
            </div>
            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        {
                        //Tee 7ks tükiks, mapi i-le päev ja kuupäev
                        [...Array(7)].map((_, i) => {
                            const d = new Date();
                            d.setDate(d.getDate() + i);
                            const weekday = new Intl.DateTimeFormat('et-EE', { weekday: 'long' }).format(d);
                            const date = d.toLocaleDateString('et-EE', { day: '2-digit', month: '2-digit' });
                            return <th key={i}> <button onClick={() => handleDayFilter(date)}>{`${weekday} (${date})`}</button> </th>
                        })
                        }
                    </tr>
                </thead>
            </table>
            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        <th>Movie Name</th>
                        <th>Genre</th>
                        <th>Language</th>
                        <th>Minimum age</th>
                        <th>Start date</th>
                        <th>Start time</th>
                        <th>Duration</th>
                        <th>Screen</th>
                        <th>Imdb rating</th>
                        <th></th> 
                    </tr>
                </thead>
                <tbody>
                    {movies
                        .flatMap(movie =>
                        movie.showtimes.map(time => ({
                            movie,
                            time})
                            ))
                        .sort((a, b) => {
                        const startTimeA = new Date(a.time.startTime);
                        const startTimeB = new Date(b.time.startTime);
                        if (startTimeA < startTimeB) return -1;
                        if (startTimeA > startTimeB) return 1;
                        return 0;
                        })
                        .map(({ movie, time }) => (
                        <tr key={time.id}>
                            <td>{movie.name}</td>
                            <td>{movie.genre}</td>
                            <td>{movie.language}</td>
                            <td>{movie.minimumAge}</td>
                            <td>{new Date(time.startTime).toLocaleDateString('en-GB')}</td>
                            <td>{new Date(time.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false })}</td>
                            <td>{time.duration}</td>
                            <td>{time.screen.id}</td>
                            <td>{movie.imdbRating}</td>
                            <td>
                            <Link to={`/showtimes/${time.id}`} className="btn btn-primary">Osta piletid</Link>
                            </td>
                        </tr>
                        ))}
                    </tbody>
            </table>
            {ratings.length > 0 && (
                <Link to={`/movies/${localStorage.getItem("customerId")}`}className="btn btn-secondary" style={{ marginBottom: "100px" }}> Soovita filme vaatamisajaloo põhjal.</Link> 
            )}
        </div>
    );
};

export default ListShowtimeComponent;
