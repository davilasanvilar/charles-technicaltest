import React, { useEffect, useState } from 'react';
import api from './api/api';
import { Modal } from './Modal';


export function NewCarForm({ onCloseDialog, refetchCars }:
    { onCloseDialog: () => void, refetchCars: () => Promise<void> }) {
    const [name, setName] = useState('')
    const [type, setType] = useState('')
    const [carTypes, setCarTypes] = useState<string[]>([])

    const getCarTypes = async () => {
        const carTypes = await api.listCarTypes()
        const types = carTypes.map((type) => type.id)
        setCarTypes(types)
    }

    const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (name !== '' && type !== '') {
            try {
                await api.createCar({ name, type })
                await refetchCars()
                onCloseDialog()
            }
            catch (error) {
                console.error(error)
            }
        }
    }

    useEffect(() => {
        getCarTypes()
    }, [])



    return (
        <Modal title='Add car' onCloseDialog={onCloseDialog}>
            <form onSubmit={onSubmit}>
                <div className='form_element'>
                    <label htmlFor='carName'>Name</label>
                    <input id="carName" type="text" value={name}
                        onChange={(event) => setName(event.target.value)} />
                </div>
                <div className='form_element'>
                    <label htmlFor='carType'>Type</label>
                    <select id="carType" value={type}
                        onChange={(event) => setType(event.target.value)} >
                        <option value="">Select a type</option>
                        {carTypes.map(type => (
                            <option key={type} value={type}>{type}</option>
                        ))}
                    </select>
                </div>
                <button type="submit">+ Create car</button>
            </form>
        </Modal>
    )
}