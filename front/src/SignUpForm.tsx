import React, { useState } from 'react';
import { useAuth } from './hooks/useAuth';
import { Modal } from './Modal';

export function SignUpForm({ onCloseDialog }: { onCloseDialog: () => void }) {
    const [username, setUsername] = useState('')
    const { signUp } = useAuth()

    const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            await signUp(username)
            onCloseDialog()
        }
        catch (error) {
            console.error(error)
        }
    }

    return (
        <Modal title='Sign Up' onCloseDialog={onCloseDialog}>
            <form onSubmit={onSubmit}>
                <label htmlFor='username'>
                    {"Username"}
                </label>
                <input id='username' type="text" value={username}
                    onChange={(event) => setUsername(event.target.value)} />
                <button type="submit">Sign up</button>
            </form>
        </Modal>
    )
}