import React, {useEffect, useState} from 'react'
import { listMovies } from '../services/MovieService'
import { listRatings } from '../services/RatingService'
import { Link } from 'react-router-dom'

const ListMovieComponent = () => {

    const [movies, setMovies] = useState([])
    const [customer, SetCustomer] = useState([])

    //REST API kutsed
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
        <h2 className='text-center'>Soovitatud filmid</h2>
        <table className='table table-striped table-bordered'>
            <thead>
                <tr>
                    <th>Filmi nimi</th>
                    <th>Žanr</th>
                    <th>Keel</th>
                    <th>Vanusepiirang</th>
                    <th>Imdb reiting</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                {
                    movies.map(movie => 
                        <tr key={movie.id}>
                            <td>{movie.name}</td>
                            <td>{movie.genre}</td>
                            <td>{movie.language}</td>
                            <td>{movie.minimumAge}</td>
                            <td>{movie.imdbRating}</td>
                            <td>
                                <Link to={`/movie/showtimes/${movie.id}`} className="btn btn-primary">Vaata aegu</Link>
                            </td> 
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