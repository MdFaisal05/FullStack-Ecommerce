import { Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";

import Home from "./pages/Home";
import AdminDashboard from "./pages/admin/AdminDashboard";

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

import ProtectedRoute from "./components/ProtectedRoute";
import AdminRoute from "./components/AdminRoute";
import ProductList from "./pages/ProductList";
import ProductDetails from "./pages/ProductDetails";

function App() {

    return (

        <>

            <Navbar />

            <Routes>

                {/* Public Routes */}

                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                {/* User Home */}
<Route
    path="/"
    element={
        <ProtectedRoute>
            <Home />
        </ProtectedRoute>
    }
/>

<Route
    path="/products"
    element={
        <ProtectedRoute>
            <ProductList />
        </ProtectedRoute>
    }
/>

<Route
    path="/product/:id"
    element={
        <ProtectedRoute>
            <ProductDetails />
        </ProtectedRoute>
    }
/>

<Route
    path="/category/:category"
    element={
        <ProtectedRoute>
            <ProductList />
        </ProtectedRoute>
    }
/>

<Route
    path="/admin"
    element={
        <AdminRoute>
            <AdminDashboard />
        </AdminRoute>
    }
/>

                <Route
                    path="/category/:category"
                    element={
                        <ProtectedRoute>
                            <ProductList />
                        </ProtectedRoute>
                    }
                />

                {/* 404 */}

                <Route
                    path="*"
                    element={<Navigate to="/" replace />}
                />

            </Routes>

            <Footer />

        </>

    );

}

export default App;