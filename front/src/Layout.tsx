import React from 'react';
import { CarTable } from './CarTable';
import { Header } from './Header';
import { useAuth } from './hooks/useAuth';
import { LoginForm } from './LoginForm';

export function Layout({ children }: { children: React.ReactNode }) {

    const { user } = useAuth()


    return (
        user ?
            user.username === 'admin' ?
                <>
                    <Header />
                    <main>
                        <CarTable />
                    </main>
                </>
                :
                <>
                    <Header />
                    <main>
                        {children}
                    </main>
                </>
            :
            <main>
                <LoginForm />
            </main>
    )
}