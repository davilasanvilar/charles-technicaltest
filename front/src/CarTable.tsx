import { useEffect, useState } from 'react';
import api from './api/api';
import { Car } from './types/entities';
import { NewCarForm } from './NewCarForm';

export function CarTable() {

    const [openCarForm, setOpenCarForm] = useState(false)

    const [carList, setCarList] = useState<Car[]>([])

    const fetchCars = async () => {
        const cars = await api.listCars()
        setCarList(cars)
    }

    useEffect(() => {
        fetchCars()
    }, [])


    return (
        <>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '32px' }}>
                <h2>{"My cars"}</h2>
                <button onClick={() => setOpenCarForm(true)}>{"+ Add new car "}</button>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                    {carList.map(car => (
                        <tr key={car.id}>
                            <td>{car.name}</td>
                            <td>{car.type.id}</td>
                            <td>{`${car.type.pricePerDay} €`}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {openCarForm && <NewCarForm onCloseDialog={() => setOpenCarForm(false)} refetchCars={fetchCars} />}
        </>
    )
}