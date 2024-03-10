import React, { useEffect, useState } from 'react';
import { listShowTimes } from '../services/ShowTimeService';

const ListMovieComponent = () => {
    const [showtimes, setShowtimes] = useState([]);

    useEffect(() => {
        listShowTimes()
            .then((response) => {
                setShowtimes(response.data);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    return (
        <div className='container'>
            <h2 className='text-center'>List of Movies</h2>
            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        <th>Movie Id</th>
                        <th>Movie Name</th>
                        <th>Start time</th>
                        <th>End time</th>
                        <th>Screen</th>
                    </tr>
                </thead>
                <tbody>
                    {showtimes.map(showtime =>
                        showtime.showtimes.map(time =>
                            <tr key={time.id}>
                                <td>{showtime.id}</td>
                                <td>{showtime.name}</td>
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
