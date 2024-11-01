import React, { useState } from 'react';
import { SignUpForm } from './SignUpForm';
import { useAuth } from './hooks/useAuth';

export function LoginForm() {


    const [username, setUsername] = useState('')
    const [openSignUp, setOpenSignUp] = useState(false)

    const { login } = useAuth()

    const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        await login(username)
    }


    return (
        <>
            <form onSubmit={onSubmit}>
                <h2>{"Login"}</h2>
                <div className='form_element'>
                    <label htmlFor='username'>
                        {"Username"}
                    </label>
                    <input id='username' type="text" value={username} onChange={(event) => setUsername(event.target.value)} />
                </div>
                <button type="submit">Login</button>
                <div style={{ display: 'flex', gap: '8px', alignItems: 'center' }}>
                    <span>{"Don't have an account yet?"}</span>
                    <button type="button" onClick={() => setOpenSignUp(true)}>Sign Up</button>
                </div>
            </form>
            {openSignUp && <SignUpForm onCloseDialog={() => setOpenSignUp(false)} />}
        </>
    )
}