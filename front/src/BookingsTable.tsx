import { useEffect, useState } from 'react';
import api from './api/api';
import { Booking } from './types/entities';
import { useAuth } from './hooks/useAuth';

export function BookingsTable() {


    const [bookingList, setBookingList] = useState<Booking[]>([])


    const { user } = useAuth()

    const fetchBookings = async () => {
        if (!user) return
        const bookings = await api.listBookings(user.id)
        setBookingList(bookings)
    }

    useEffect(() => {
        fetchBookings()
    }, [])

    const onReturnCar = async (bookingId: string) => {
        await api.returnCar(bookingId)
        await fetchBookings()
    }

    return (
        <>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '32px' }}>
                <h2>{"My bookings"}</h2>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th> Start date</th>
                        <th> End date</th>
                        <th> Expected price</th>
                        <th> Return date</th>
                        <th> Total price</th>
                        <th>Return car</th>
                    </tr>
                </thead>
                <tbody>
                    {bookingList.map(booking => (
                        <tr key={booking.id}>
                            <td>{booking.car.name}</td>
                            <td>{booking.car.type.id}</td>
                            <td>{new Date(booking.startDate).toISOString()}</td>
                            <td>{new Date(booking.endDate).toISOString()}</td>
                            <td>{`${booking.expectedPrice} €`}</td>
                            <td> {booking.returnDate ? new Date(booking.returnDate).toISOString() : ''}</td>
                            <td>{`${booking.totalPrice ? booking.totalPrice : '-'} €`}</td>
                            <td> {booking.returnDate ? '' : <button onClick={() => onReturnCar(booking.id)}>{"Return car"}</button>}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    )
}