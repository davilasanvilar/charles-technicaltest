export interface User {
    id: string;
    username: string;
}

export interface CarType {
    id: string;
    pricePerDay: number;
}

export interface Car {
    id: string;
    name: string;
    type: CarType;
}

export interface CarForm {
    name: string;
    type: string;
}


export interface SearchAvailableCarsForm {
    startDate: Date;
    endDate: Date;
}

export interface CarTypeAvailability {
    carType: CarType;
    availableCars: number;
}

export interface RentForm {
    carType: string;
    userId: string;
    startDate: Date;
    endDate: Date;
}

export interface Booking {
    id: string;
    car: Car;
    user: User;
    startDate: Date;
    endDate: Date;
    returnDate: Date;
    expectedPrice: number;
    totalPrice: number;
    createdAt: Date;
}