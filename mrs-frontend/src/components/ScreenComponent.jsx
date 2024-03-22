import React, { useEffect, useState } from 'react';
import { useParams, useLocation,useNavigate } from 'react-router-dom';
import { getScreen } from '../services/ScreenService';
import { setMovieRating } from '../services/RatingService';
import availableChairImage from '../assets/images/availableChairImage.png';
import recommChairImage from '../assets/images/recommChair.png';
import '../css/Screen.css'

import { Link } from 'react-router-dom'

const ScreenComponent = () => {
  const { screenId } = useParams();
  const [screen, setScreen] = useState(null);
  const [seats, setSeats] = useState([]);
  const [recommSeats, setRecommSeats] = useState([]);
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const ticketCount = queryParams.get('ticketCount');
  const movieId = queryParams.get('movieId');
  const navigate = useNavigate();

  const [movieRating] = useState({
    movie: {id: movieId,},
    customer:{id: localStorage.getItem('customerId'),}
});

//REST API kutsed
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

  const handleMovieRating = async (e) => {
    //Laseme kasutajal filmi hinnata
    e.preventDefault();
    let userRating = prompt('Please rate the movie (out of 5):');
    userRating = parseInt(userRating);
    if (isNaN(userRating) || userRating < 1 || userRating > 5) {
      alert('Please enter a valid rating between 1 and 5.');
      return;
    }
  
    const updatedRating = {
      ...movieRating,
      rating: userRating
    };
    
    //Juhul kui kasutaja ei ole filmi vaadanud, lisame selle filmi vaadatud filmide andmebaasi.
    try {
      await setMovieRating(updatedRating);
      alert('Film edukalt vaadatud!');
      navigate('/');
    } catch (error) {
      alert("Seda filmi oled juba vaadanud!");
      navigate('/');
    }
  };

  //Tekitame saali istumisplaani
  const renderSeatingPlan = () => {
    if (!screen) return null;
    const plan = [];
    for (let i = 0; i < screen.rows; i++) {
      const rowSeats = [];
      for (let j = 0; j < screen.seatsInRow; j++) {
        const seatIndex = i * screen.seatsInRow + j;
        //Anname istekohale vastavalt klassi kas ta on vaba, kinni või soovitatav.
        let seatStatus = seats[seatIndex] ? 'available' : 'occupied';
        let isRecommended = recommSeats && recommSeats.includes(seatIndex);
        if (isRecommended) {
          seatStatus = 'recommend';
        }
        rowSeats.push(
          //Vastavalt istekoha klassile stiliseerime selle.
          <td key={j} className={seatStatus}>
            <img src={isRecommended ? recommChairImage : availableChairImage} alt={`Iste ${i + 1}-${j + 1}`} />
            <div className="seat-number">{j + 1}</div>
          </td>
        );
      }
      plan.push(
        //Lisame reanumbrid
        <div className="seating-row" key={`row-${i}`}>
          <div className="row-label">Rida {screen.rows - i}</div>
          <table><tbody><tr>{rowSeats}</tr></tbody></table>
        </div>
      );
    }
    return <div className="seating-plan">{plan}</div>;
  };

  return (
    <div className='screencontainer'>
      <div className="seating-plan-container">
        {screen && (
          <div>
            {renderSeatingPlan()}
            <div className="screen-info">
              <p className="Screen">Saal {screen.id}</p>
              <div className="buttons-container">
                <Link to={`/showtimes/`} className="btn btn-primary">Tühista</Link>
                <Link to={`/`} className="btn btn-primary" onClick={handleMovieRating}>Kinnita</Link>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default ScreenComponent;
