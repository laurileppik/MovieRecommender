import React, {useEffect, useState} from 'react'
import { listMovies } from '../services/MovieService'

const ListMovieComponent = () => {
    //Comment

    const [movies, setMovies] = useState([])

    useEffect(() => {
        listMovies().then((response) => {
            setMovies(response.data);
        }).catch(error => {
            console.error(error);
        })
    }, []) 

  return (
    <div className='container'>
        <h2 className='text-center'>List of Movies</h2>
        <table className='table table-striped table-bordered'>
            <thead>
                <tr>
                    <th>Movie Id</th>
                    <th>Movie Name</th>
                    <th>Genre</th>
                    <th>Language</th>
                    <th>Start time</th>
                    <th>End time</th>
                    <th>Minimum age</th>
                </tr>
            </thead>
            <tbody>
                {
                    movies.map(movie => 
                        <tr key={movie.id}>
                            <td>{movie.id}</td>
                            <td>{movie.name}</td>
                            <td>{movie.genre}</td>
                            <td>{movie.language}</td>
                            <td>{movie.startTime}</td>
                            <td>{movie.endTime}</td>
                            <td>{movie.minimumAge}</td>
                        </tr>)
                }
                <tr>

                </tr>
            </tbody>
        </table>
    </div>
  )
}

export default ListMovieComponent