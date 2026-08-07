import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {

    const { user, logout } = useAuth();

    const navigate = useNavigate();

    const handleLogout = () => {

        logout();

        navigate("/login");

    };

    return (

        <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow">

            <div className="container">

                <Link
                    className="navbar-brand fw-bold"
                    to="/"
                >
                    🛒 LazyMarket
                </Link>

                <button
                        className="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarContent"
                >

                    <span className="navbar-toggler-icon"></span>

                </button>

                <div
                        className="collapse navbar-collapse"
                        id="navbarContent"
                >

                    <ul className="navbar-nav me-auto">

                        <li className="nav-item">

                            <Link
                                    className="nav-link"
                                    to="/"
                            >
                                Home
                            </Link>

                        </li>

                        <li className="nav-item">

                            <Link
                                    className="nav-link"
                                    to="/products"
                            >
                                Products
                            </Link>

                        </li>

                        <li className="nav-item">

                            <Link
                                    className="nav-link"
                                    to="/categories"
                            >
                                Categories
                            </Link>

                        </li>

                        {

                            user && (

                                    <>

                                        <li className="nav-item">

                                            <Link
                                                    className="nav-link"
                                                    to="/cart"
                                            >
                                                Cart
                                            </Link>

                                        </li>

                                        <li className="nav-item">

                                            <Link
                                                    className="nav-link"
                                                    to="/orders"
                                            >
                                                My Orders
                                            </Link>

                                        </li>

                                    </>

                            )

                        }

                        {

                            user?.role === "ROLE_ADMIN" && (

                                    <li className="nav-item">

                                        <Link
                                                className="nav-link text-warning"
                                                to="/admin"
                                        >
                                            Admin Dashboard
                                        </Link>

                                    </li>

                            )

                        }

                    </ul>

                    <ul className="navbar-nav ms-auto">

                        {

                            !user ? (

                                    <>

                                        <li className="nav-item">

                                            <Link
                                                    className="nav-link"
                                                    to="/login"
                                            >
                                                Login
                                            </Link>

                                        </li>

                                        <li className="nav-item">

                                            <Link
                                                    className="nav-link"
                                                    to="/register"
                                            >
                                                Register
                                            </Link>

                                        </li>

                                    </>

                            ) : (

                                    <>

                                        <li className="nav-item">

                                            <span
                                                    className="navbar-text text-white me-3"
                                            >

                                                Welcome,

                                                {" "}

                                                <strong>

                                                    {user.firstName}

                                                </strong>

                                            </span>

                                        </li>

                                        <li className="nav-item">

                                            <button
                                                    className="btn btn-danger"
                                                    onClick={handleLogout}
                                            >
                                                Logout
                                            </button>

                                        </li>

                                    </>

                            )

                        }

                    </ul>

                </div>

            </div>

        </nav>

    );

}

export default Navbar;