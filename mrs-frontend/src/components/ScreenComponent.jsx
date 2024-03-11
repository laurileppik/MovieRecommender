import React, { useEffect, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { getScreen } from '../services/ScreenService';

const ScreenComponent = () => {
  const { screenId } = useParams();
  const [screen, setScreen] = useState(null);
  const [seats, setSeats] = useState([]);

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const ticketCount = queryParams.get('ticketCount');

  useEffect(() => {
    const fetchScreen = async () => {
      try {
        const response = await getScreen(screenId);
        setScreen(response.data);
        setSeats(response.data.occupiedSeats);
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
        const seatStatus = seats[seatIndex] ? 'available' : 'occupied';
        rowSeats.push(
          <td key={j} className={seatStatus}>
            Seat {seatIndex + 1}
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
    <div className="seating-plan-container">
      {screen && (
        <div>
          <p>{screen.id}</p>
          <p>{screen.noOfSeats}</p>
          <p>{screen.rows}</p>
          <p>{screen.seatsInRow}</p>
          <p>{screen.occupiedSeats}</p>
          {renderSeatingPlan()}
          <p>Saal {screen.id}</p>
        </div>
      )}
    </div>
  );
};

export default ScreenComponent;
