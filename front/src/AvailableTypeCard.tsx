import sedan from '../public/sedan.jpg'
import suv from '../public/suv.webp'
import van from '../public/van.jpg'




const getImg = (type: string) => {
    switch (type) {
        case 'Sedan':
            return sedan
        case 'SUV':
            return suv
        case 'van':
            return van
        default:
            return van
    }
}
export function AvailableCarsCard({ type, numberOfCars, pricePerDay, onRentCar }:
    { type: string, numberOfCars: number, pricePerDay: number, onRentCar: (type: string) => void }) {

    const noCars = numberOfCars < 1

    return (
        <div className='available_card' style={{ cursor: noCars ? 'default' : 'pointer' }}
            onClick={() => numberOfCars > 0 ? onRentCar(type) : undefined}>
            <h3>{type}</h3>
            <img src={getImg(type)} alt={type} style={{ width: '200px' }} />
            <p>{`${pricePerDay} €/day`}</p>
            <p style={{ color: noCars ? 'red' : undefined }}>Available cars: {numberOfCars}</p>
        </div>
    )
}