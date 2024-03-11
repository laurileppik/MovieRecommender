import './App.css'
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import ListMovieComponent from './components/ListMovieComponent'
import ListShowtimeComponent from './components/ListShowtimeComponent'
import ShowtimeComponent from './components/ShowtimeComponent';

import { BrowserRouter, Routes, Route } from 'react-router-dom'

function App() {

  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
          <Routes>
            <Route path='/' element= {<ListShowtimeComponent />}> </Route>
            <Route path='/movies' element= {<ListMovieComponent />}></Route>
            <Route path='/showtimes' element= {<ListShowtimeComponent />}></Route>
            <Route path='/showtimes/:showtimeID' element={<ShowtimeComponent />} />
          </Routes>
        <FooterComponent />
      </BrowserRouter>
    </>
  )
}

export default App
