import { useEffect, useState } from 'react';
import { AvailableCarsCard } from './AvailableTypeCard';
import { NewBookingForm } from './NewBookingForm';
import api from './api/api';
import { useAuth } from './hooks/useAuth';
import { CarTypeAvailability, RentForm } from './types/entities';

export function CarPicker() {

    const [startDate, setStartDate] = useState('')
    const [endDate, setEndDate] = useState('')

    const [rentForm, setRentForm] = useState<RentForm | undefined>()

    const [availableCars, setAvailableCars] = useState<CarTypeAvailability[]>([])

    const { user } = useAuth()

    useEffect(() => {
        if (startDate && endDate) {
            searchAvailableCars()
        }
    }, [startDate, endDate])

    const searchAvailableCars = async () => {
        const cars = await api.getAvailableCars(new Date(startDate), new Date(endDate))
        setAvailableCars(cars)
    }

    const onRentCar = (carType: string) => {
        if (user) {
            setRentForm({
                carType,
                startDate: new Date(startDate),
                endDate: new Date(endDate),
                userId: user.id
            })
        }
    }

    return (
        <>
            <h2>Rent a car</h2>
            <div style={{ display: 'flex', gap: "20px" }}>
                <div className='form_element'>
                    <label htmlFor='startDate'>Start date</label>
                    <input id='startDate' type="datetime-local" value={startDate}
                        onChange={(event) => setStartDate(event.target.value)} />
                </div>

                <div className='form_element'>
                    <label htmlFor='endDate'>End date</label>
                    <input id='endDate' type="datetime-local" value={endDate}
                        onChange={(event) => setEndDate(event.target.value)} />
                </div>
            </div>
            {startDate && endDate ?

                <div style={{
                    display: 'grid',
                    marginTop: '20px',
                    gap: '20px',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(250px,1fr)'
                }}>
                    {availableCars.map(car => (
                        <AvailableCarsCard key={car.carType.id} type={car.carType.id}
                            numberOfCars={car.availableCars} pricePerDay={car.carType.pricePerDay}
                            onRentCar={onRentCar}
                            />
                    ))}
                </div> :
                <p>Please select a start and end date to see available cars</p>
            }
            {rentForm && <NewBookingForm rentForm={rentForm} onCloseDialog={() => setRentForm(undefined)}
                refetchAvailableCars={searchAvailableCars} />}
        </>
    )
}