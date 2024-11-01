import { useAuth } from './hooks/useAuth';
import { useNavigate } from 'react-router-dom';

export function Header() {

    const { user, logout } = useAuth()

    const navigate = useNavigate()

    return (
        <header>
            <nav>
                <ul>
                    <li>
                        <a onClick={() => navigate("/")}>Home</a>
                    </li>
                    <li>
                        <a onClick={() => navigate("/bookings")}>Bookings</a>
                    </li>
                </ul>
            </nav>
            <div>
                <h3>Welcome {user?.username}</h3>
                <button onClick={() => logout()}>{"Logout"}</button>
            </div>
        </header>
    )
}