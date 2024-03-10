import React, { useEffect, useState } from 'react';
import { listShowTimes } from '../services/ShowTimeService';

const ListMovieComponent = () => {
    const [movies, setMovies] = useState([]);
    const [selectedGenre, setSelectedGenre] = useState('');

    useEffect(() => {
        const fetchShowTimes = async () => {
            try {
                const response = await listShowTimes(selectedGenre);
                setMovies(response.data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchShowTimes();
    }, [selectedGenre]);

    const handleGenreChange = (event) => {
        setSelectedGenre(event.target.value);
    };

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
            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        <th>Movie Id</th>
                        <th>Movie Name</th>
                        <th>Genre</th>
                        <th>Language</th>
                        <th>Minimum age</th>
                        <th>Start time</th>
                        <th>End time</th>
                        <th>Screen</th>
                    </tr>
                </thead>
                <tbody>
                    {movies.map(movie =>
                        movie.showtimes.map(time =>
                            <tr key={time.id}>
                                <td>{movie.id}</td>
                                <td>{movie.name}</td>
                                <td>{movie.genre}</td>
                                <td>{movie.language}</td>
                                <td>{movie.minimumAge}</td>
                                <td>{time.startTime}</td>
                                <td>{time.endTime}</td>
                                <td>{time.screen.id}</td>
                            </tr>
                        )
                    )}
                </tbody>
            </table>
        </div>
    );
};

export default ListMovieComponent;
