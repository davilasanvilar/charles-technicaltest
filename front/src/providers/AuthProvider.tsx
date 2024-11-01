import React, { useState } from 'react';
import conf from '../conf';
import { User } from '../types/entities';


interface AuthContext {
    user: User | undefined;
    login: (username: string) => void;
    logout: () => void;
    signUp: (username: string) => void;
}

export const AuthContext = React.createContext<AuthContext>({} as AuthContext)

export function AuthProvider({ children }: { children: React.ReactNode }) {

    const [user, setUser] = useState<User | undefined>(undefined)

    const login = async (username: string) => {
        const url = `${conf.API_URL}signin`
        const body = JSON.stringify({ username })
        const response = await fetch(url, {
            body,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        const user = await response.json()
        setUser(user)
    }

    const logout = () => {
        setUser(undefined)
    }

    const signUp = async (username: string) => {
        const url = `${conf.API_URL}signup`
        const body = JSON.stringify({ username })
        await fetch(url, {
            body,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }

    const authContextValue: AuthContext = {
        user,
        login,
        logout,
        signUp
    }

    return (
        <AuthContext.Provider value={authContextValue}>
            {children}
        </AuthContext.Provider>
    )
}