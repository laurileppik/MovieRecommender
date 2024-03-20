import './App.css'
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import ListMovieComponent from './components/ListMovieComponent'
import ListShowtimeComponent from './components/ListShowtimeComponent'
import ShowtimeComponent from './components/ShowtimeComponent';
import ScreenComponent from './components/ScreenComponent'
import CustomerComponent from './components/CustomerComponent'
import LoginPage from './components/LoginPage'
import SignupComponent from './components/SignUpComponent'
import AddMovieComponent from './components/admin/AddMovieComponent'
import AddShowTimesComponent from './components/admin/AddShowTimesComponent'

import { BrowserRouter, Routes, Route } from 'react-router-dom'


function App() {

  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
          <Routes>
            <Route path='/' element= {<ListShowtimeComponent />}> </Route>
            <Route path='/movies/:customerId' element= {<ListMovieComponent />}></Route>
            <Route path='/showtimes' element= {<ListShowtimeComponent />}></Route>
            <Route path='/showtimes/:showtimeID' element={<ShowtimeComponent />} />
            <Route path='/screens/:screenId' element={<ScreenComponent />} />
            <Route path='/customers/:customerID' element={<CustomerComponent />} />
            <Route path='/login' element={<LoginPage />} />
            <Route path='/signup' element={<SignupComponent />} />
            <Route path='/admin/addmovie' element={<AddMovieComponent />} />
            <Route path='/admin/addshowtime' element={<AddShowTimesComponent />} />
            <Route path="/movie/showtimes/:filterMovieId" element= {<ListShowtimeComponent />}></Route>
          </Routes>
        <FooterComponent />
      </BrowserRouter>
    </>
  )
}

export default App
