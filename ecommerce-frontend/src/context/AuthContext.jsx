import { createContext, useContext, useEffect, useState } from "react";
import authService from "../services/AuthService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const [user, setUser] = useState(null);

    const [loading, setLoading] = useState(true);

    useEffect(() => {

        const token = localStorage.getItem("token");

        if (!token) {

            setLoading(false);
            return;

        }

        authService
            .getCurrentUser()
            .then((res) => {

                setUser(res.data);

            })
            .catch(() => {

                localStorage.removeItem("token");

                localStorage.removeItem("role");

                setUser(null);

            })
            .finally(() => {

                setLoading(false);

            });

    }, []);

    const login = (loginResponse) => {

        localStorage.setItem(
            "token",
            loginResponse.token
        );

        localStorage.setItem(
            "role",
            loginResponse.role
        );

        setUser(loginResponse.user);

    };

    const logout = () => {

        localStorage.removeItem("token");

        localStorage.removeItem("role");

        setUser(null);

    };

    return (

        <AuthContext.Provider
            value={{
                user,
                login,
                logout,
                loading
            }}
        >

            {children}

        </AuthContext.Provider>

    );

};

export const useAuth = () => useContext(AuthContext);