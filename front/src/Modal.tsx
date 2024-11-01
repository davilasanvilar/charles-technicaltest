import React from 'react';

export function Modal({ children, title, onCloseDialog }:
    { children: React.ReactNode, title: string, onCloseDialog: () => void, }) {

    return (
        <div className='dialog_overlay'>
            <dialog open={true}>
                <div className='dialog_header'>
                    <h2>{title}</h2>
                    <button onClick={onCloseDialog}>X</button>
                </div>
                {children}
            </dialog>
        </div>
    )
}