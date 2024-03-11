import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getShowTime } from '../services/ShowTimeService';

const ShowtimeComponent = () => {
  const { showtimeID } = useParams();
  const [showtime, setShowtime] = useState(null);

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

  return (
    <div>
      {showtime && (
        <div>
          <h2>Showtime Details</h2>
          <p>ID: {showtime.id}</p>
          <p>Movie ID: {showtime.movieId}</p>
          <p>Screen ID: {showtime.screenId}</p>
          <p>Start Time: {showtime.startTime}</p>
          <p>End Time: {showtime.endTime}</p>
        </div>
      )}
    </div>
  );
};

export default ShowtimeComponent;
