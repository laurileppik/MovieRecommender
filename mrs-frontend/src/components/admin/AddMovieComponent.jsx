import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { setMovie } from '../../services/MovieService';

const AddMovieComponent = () => {
    const [movie, setMovieState] = useState({
        name: '',
        genre: '',
        language: '',
        minimumAge: '',
        imdbRating: '',
    });
    const [suggestedMovies, setSuggestedMovies] = useState([]);
    const navigate = useNavigate();

    //Otsime OMDb API abil filme, mis algavad samamoodi nagu kasutaja sisestab
    const handleSuggestion = async (query) => {
        try {
            const response = await fetch(`https://www.omdbapi.com/?apikey=fc0dea0b&s=${query}`);
            const data = await response.json();
            if (data.Search) {
                setSuggestedMovies(data.Search);
            } else {
                setSuggestedMovies([]);
            }
        } catch (error) {
            console.error('Error fetching movie suggestions:', error);
        }
    };

    //Kui vajutame filmi peale, siis lisatakse film lünka
    const handleSuggestionClick = (movie) => {
        setMovieState({
            ...movie,
            name: movie.Title,
        });
        setSuggestedMovies([]);
    };

    //REST API filmi lisamiseks
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await setMovie(movie);
            alert('Movie added successfully');
            navigate('/');
        } catch (error) {
            alert(error.message);
        }
    };

    //Meetod juhuks kui väärtus lünga sees muutub
    const handleChange = (e) => {
        const { name, value } = e.target;
        setMovieState({
            ...movie,
            [name]: value,
        });
        if (name === 'name') {
            handleSuggestion(value);
        }
    };

    return (
        <div>
            <h2>Lisa film</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nimi:</label>
                    <input
                        type="text"
                        name="name"
                        value={movie.name}
                        onChange={handleChange}
                        required
                    />
                    {suggestedMovies.length > 0 && (
                        <div style={{ marginTop: '5px' }}>
                            {suggestedMovies.map((movie) => (
                                <button key={movie.imdbID} type="button" onClick={() => handleSuggestionClick(movie)}
                                    style={{ cursor: 'pointer', display: 'block', marginBottom: '5px' }}>
                                    {movie.Title}
                                </button>
                            ))}
                        </div>
                    )}
                </div>
                <div>
                    <label>Minimaalne vanus:</label>
                    <input
                        type="number"
                        name="minimumAge"
                        value={movie.minimumAge}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Lisa film</button>
            </form>
        </div>
    );
};

export default AddMovieComponent;
