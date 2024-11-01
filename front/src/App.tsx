import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { HomesScreen } from './routes/HomeScreen';
import { BookingsScreen } from './routes/BookingsScreen';

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomesScreen />} />
        <Route path="/bookings" element={<BookingsScreen />} />
      </Routes>
    </BrowserRouter>

  )
}

export default App
