import { useContext } from 'react';
import { AuthContext } from '../providers/AuthProvider';

export function useAuth() {
    const authContext = useContext(AuthContext)
    if (authContext === undefined) {
        throw new Error('useAuth must be used within an AuthProvider')
    } else {
        return authContext
    }
}