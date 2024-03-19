import React, { useEffect, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { getScreen } from '../services/ScreenService';
import availableChairImage from '../assets/images/availableChairImage.png';
import recommChairImage from '../assets/images/recommChair.png';

import { Link } from 'react-router-dom'

const ScreenComponent = () => {
  const { screenId } = useParams();
  const [screen, setScreen] = useState(null);
  const [seats, setSeats] = useState([]);
  const [recommSeats, setRecommSeats] = useState([]);

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const ticketCount = queryParams.get('ticketCount');

  useEffect(() => {
    const fetchScreen = async () => {
      try {
        const response = await getScreen(screenId, ticketCount);
        setScreen(response.data);
        setSeats(response.data.occupiedSeats);
        setRecommSeats(response.data.recommendedSeats)
      } catch (error) {
        console.error(error);
      }
    };

    fetchScreen();
  }, [screenId, ticketCount]);

  const renderSeatingPlan = () => {
    if (!screen) return null;

    const rows = [];
    for (let i = 0; i < screen.rows; i++) {
      const rowSeats = [];
      for (let j = 0; j < screen.seatsInRow; j++) {
        const seatIndex = i * screen.seatsInRow + j;
        let seatStatus = seats[seatIndex] ? 'available' : 'occupied';
        if (recommSeats && recommSeats.includes(seatIndex)){
            console.log(seatStatus)
            seatStatus = 'recommend'
        }
        rowSeats.push(
          <td key={j} className={seatStatus}>
            <img src={seatStatus === 'recommend' ? recommChairImage : availableChairImage} alt={`Seat ${seatIndex + 1}`} />
          </td>
        );
      }
      rows.push(<tr key={i}>{rowSeats}</tr>);
    }
    return (
      <table className="seating-plan">
        <tbody>{rows}</tbody>
      </table>
    );
  };

  return (
    <div className='screencontainer'>
      <div className="seating-plan-container">
        {screen && (
          <div>
            {renderSeatingPlan()}
            <p>Saal {screen.id}</p>
          </div>
        )}
      </div>
    <Link to={`/showtimes/`} className="btn btn-primary">Tühista</Link>
  </div>
  );
};

export default ScreenComponent;
