import React, {useEffect, useState} from 'react'
import { listMovies } from '../services/MovieService'
import { listRatings } from '../services/RatingService'

const ListMovieComponent = () => {

    const [movies, setMovies] = useState([])
    const [customerRatings, SetCustomer] = useState([])

    useEffect(() => {
        listMovies(localStorage.getItem("customerId")).then((response) => {
            setMovies(response.data);
            console.log(response.data)
        }).catch(error => {
            console.error(error);
        })
    }, []) 

    useEffect(() => {
        listRatings(localStorage.getItem("customerId")).then((response) => {
            SetCustomer(response.data);
            console.log(response.data)
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