import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './layout/Header';
import Home from './component/Home';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-calendar/dist/Calendar.css';
import Footer from './layout/Footer';
import '@fortawesome/fontawesome-free/css/all.min.css';
import Login from './component/Login';
import { createContext, useReducer } from 'react';
import MyUserReducer from './reducers/MyUserReducer';
import cookie from 'react-cookies'
import { Container } from 'react-bootstrap';
import InfoAcount from './component/InfoAccount';
import ChangePassword from './component/ChangePassword';
import Register from './component/patient/Register';
import Appointment from './component/patient/Appointment';
import ListAppointment from './component/patient/ListAppointment';

export const MyUserContext = createContext()

function App() {
  const [currentUser, stateUser] = useReducer(MyUserReducer, cookie.load('user')|| null)


  return (
    <MyUserContext.Provider value={[currentUser, stateUser]}>
      <BrowserRouter>
      <Header />
      {/* <Container> */}
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/login' element={<Login />} />
        <Route path='/account-setting' element={<InfoAcount />} />
        <Route path='/account-setting/change-password' element={<ChangePassword />} />
        <Route path='/register' element={<Register />} />
        <Route path='/appointment' element={<Appointment />} />
        <Route path='/list-appointment' element={<ListAppointment />} />
      </Routes>
      {/* </Container> */}
      <Footer />
    </BrowserRouter>
    </MyUserContext.Provider>
  );
}

export default App;
