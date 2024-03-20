import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getShowTime } from '../services/ShowTimeService';
import { getMovie } from '../services/MovieService'

import { Link } from 'react-router-dom'

const ShowtimeComponent = () => {
  const { showtimeID } = useParams();
  const [showtime, setShowtime] = useState(null);
  const [movie, setMovie] = useState(null);
  const [ticketCount, setTicketCount] = useState(0);

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

  const handleSelectTicket = () => {
    setTicketCount(ticketCount + 1);
  };

  const handleDeselectTicket = () => {
    if (ticketCount > 0) {
      setTicketCount(ticketCount - 1);
    }
  };

  return (
    <div>
      {showtime && movie && (
        <div>
          <h2>Showtime Details</h2>
          <p>ID: {showtime.id}</p>
          <p>Movie ID: {showtime.movieId}</p>
          <p>Screen ID: {showtime.screenId}</p>
          <p>Start Time: {showtime.startTime}</p>
          <p>End Time: {showtime.endTime}</p>
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
