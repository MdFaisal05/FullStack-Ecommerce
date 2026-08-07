import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function UserRoute({ children }) {

    const { user, loading } = useAuth();

    if (loading) {

        return (

            <div className="container mt-5 text-center">

                <h4>Loading...</h4>

            </div>

        );

    }

    if (!user) {

        return <Navigate to="/login" replace />;

    }

    if (

        user.role !== "ROLE_USER" &&

        user.role !== "ROLE_ADMIN"

    ) {

        return <Navigate to="/login" replace />;

    }

    return children;

}

export default UserRoute;