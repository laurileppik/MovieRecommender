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

    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setMovieState({
            ...movie,
            [name]: value,
        });
    };

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

    return (
        <div>
            <h2>Add Movie</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name:</label>
                    <input
                        type="text"
                        name="name"
                        value={movie.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Minimum Age:</label>
                    <input
                        type="number"
                        name="minimumAge"
                        value={movie.minimumAge}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Add Movie</button>
            </form>
        </div>
    );
};

export default AddMovieComponent;
