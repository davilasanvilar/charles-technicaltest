import React, { useState } from 'react';
import api from './api/api';
import { Modal } from './Modal';
import { Booking, RentForm } from './types/entities';

export function NewBookingForm({ rentForm, onCloseDialog, refetchAvailableCars }:
    { rentForm: RentForm, onCloseDialog: () => void, refetchAvailableCars: () => Promise<void> }) {

    const [bookingResult, setBookingResult] = useState<Booking | undefined>(undefined)

    const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            const booking = await api.rentCar(rentForm)
            setBookingResult(booking)
            await refetchAvailableCars()
        }
        catch (error) {
            console.error(error)
        }
    }


    return (
        <Modal title='Rent car' onCloseDialog={onCloseDialog}>
            {bookingResult === undefined ?
                <form onSubmit={onSubmit}>
                    <div className='card_element'>
                        <span>{rentForm.carType}</span>
                    </div>
                    <div className='card_element'>
                        <span>{"From:"}</span>
                        <span>{rentForm.startDate.toISOString()}</span>
                    </div>
                    <div className='card_element'>
                        <span>{"To:"}</span>
                        <span>{rentForm.endDate.toISOString()}</span>
                    </div>
                    <span>{"Are you sure you want to confirm?"}</span>
                    <button type="submit">Confirm</button>
                    <button type="button" onClick={onCloseDialog}>Cancel</button>
                </form>
                :
                <div>
                    <div className='card_element'>
                        <span>{"Name:"}</span>
                        <span>{bookingResult.car.name}</span>
                    </div>
                    <div className='card_element'>
                        <span>{"Type:"}</span>
                        <span>{bookingResult.car.type.id}</span>
                    </div>

                </div>
            }

        </Modal>
    )
}