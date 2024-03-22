import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getShowTime } from '../services/ShowTimeService';
import { getMovie } from '../services/MovieService'
import '../css/Showtime.css'

import { Link } from 'react-router-dom'

const ShowtimeComponent = () => {
  const { showtimeID } = useParams();
  const [showtime, setShowtime] = useState(null);
  const [movie, setMovie] = useState(null);
  const [ticketCount, setTicketCount] = useState(0);

  //REST API kutsed
  useEffect(() => {
    const fetchShowtime = async () => {
      try {
        const response = await getShowTime(showtimeID);
        setShowtime(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchShowtime();
  }, [showtimeID]);

  useEffect(() => {
    if (showtime && showtime.movieId) {
      const fetchMovie = async () => {
        try {
          const response = await getMovie(showtime.movieId);
          setMovie(response.data);
        } catch (error) {
          console.error(error);
        }
      };
  
      fetchMovie();
    }
  }, [showtime]);

  //Loogika piletite arvu meeleshoidmiseks
  const handleSelectTicket = () => {
    setTicketCount(ticketCount + 1);
  };

  const handleDeselectTicket = () => {
    if (ticketCount > 0) {
      setTicketCount(ticketCount - 1);
    }
  };

  return (
    <div className="centered-div">
      {showtime && movie && (
        
        <div className= "details">
          <h2>Showtime Details</h2>
          <p>Start Date: {new Date(showtime.startTime).toLocaleDateString('en-GB')}</p>
          <p>Start time: {new Date(showtime.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false })}</p>
          <p>Duration: {showtime.duration}</p>
          <p>Movie: {movie.name}</p>
          <button onClick={handleSelectTicket}>+</button>
          <button onClick={handleDeselectTicket}>-</button>
          <p>Tickets: {ticketCount}</p>
          <Link to={`/screens/${showtime.screenId}?ticketCount=${ticketCount}&movieId=${showtime.movieId}`} className="btn btn-primary">Vali kohad</Link>
        </div>
      )}
    </div>
  );
};

export default ShowtimeComponent;
