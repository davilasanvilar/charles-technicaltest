import conf from "../conf";
import { Booking, Car, CarForm, CarType, CarTypeAvailability, RentForm } from "../types/entities";

interface Api {
    createCar: (car: CarForm) => Promise<Car>;
    listCars: () => Promise<Car[]>;
    getAvailableCars: (startDate: Date, endDate: Date) => Promise<CarTypeAvailability[]>;
    rentCar: (rentForm: RentForm) => Promise<Booking>;
    listCarTypes: () => Promise<CarType[]>;
    listBookings: (userId: string) => Promise<Booking[]>;
    returnCar: (bookingId: string) => Promise<Booking>;
}

const api: Api = {
    createCar: async (car: CarForm) => {
        const url = `${conf.API_URL}cars`;

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(car),
        }
        );
        const result = await response.json();
        return result;
    },
    listCars: async () => {
        const url = `${conf.API_URL}cars`;

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        }
        );
        const result = await response.json();
        return result;
    },
    getAvailableCars: async (startDate: Date, endDate: Date) => {

        const url = `${conf.API_URL}cars/available?startDate=${startDate.toISOString()}&endDate=
        ${endDate.toISOString()}`;

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        }
        );
        const result = await response.json();
        return result;
    },
    rentCar: async (rentForm: RentForm) => {
        const url = `${conf.API_URL}cars/rent`;

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(rentForm),
        }
        );
        const result = await response.json();
        return result;
    },
    listCarTypes: async () => {
        const url = `${conf.API_URL}cars/types`;

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        }
        );
        const result = await response.json();
        return result;
    },
    listBookings: async (userId: string) => {
        const url = `${conf.API_URL}bookings?userId=${userId}`;

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        }
        );
        const result = await response.json();
        return result;
    },
    returnCar: async (bookingId: string) => {
        const url = `${conf.API_URL}bookings/${bookingId}/return`;

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        }
        );
        const result = await response.json();
        return result;
    },
};

export default api;