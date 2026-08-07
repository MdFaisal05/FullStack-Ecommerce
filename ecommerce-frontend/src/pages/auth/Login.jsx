import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

import authService from "../../services/AuthService";
import { useAuth } from "../../context/AuthContext";

function Login() {

    const navigate = useNavigate();

    const { login } = useAuth();

    const [loading, setLoading] = useState(false);

    const [form, setForm] = useState({

        email: "",

        password: ""

    });

    const handleChange = (e) => {

        setForm({

            ...form,

            [e.target.name]: e.target.value

        });

    };

   const handleSubmit = async (e) => {

       e.preventDefault();

       try {

           setLoading(true);

       const response = await authService.login(form);

       login(response.data);

       toast.success(response.data.message);

       if (response.data.role === "ROLE_ADMIN") {
           navigate("/admin");
       } else {
           navigate("/");
       }

       } catch (error) {

           toast.error(

               error.response?.data?.message ||

               "Invalid Email Or Password"

           );

       } finally {

           setLoading(false);

       }

   };

    return (

        <div className="container mt-5">

            <div className="row justify-content-center">

                <div className="col-md-5">

                    <div className="card shadow">

                        <div className="card-body">

                            <h3 className="text-center mb-4">

                                Login

                            </h3>

                            <form onSubmit={handleSubmit}>

                                <div className="mb-3">

                                    <label>Email</label>

                                    <input

                                        type="email"

                                        name="email"

                                        className="form-control"

                                        value={form.email}

                                        onChange={handleChange}

                                        required

                                    />

                                </div>

                                <div className="mb-3">

                                    <label>Password</label>

                                    <input

                                        type="password"

                                        name="password"

                                        className="form-control"

                                        value={form.password}

                                        onChange={handleChange}

                                        required

                                    />


                                    <p className="text-center">

                                        Don't have an account?

                                        <Link
                                            to="/register"
                                            className="ms-2"
                                        >

                                            Register

                                        </Link>

                                    </p>

                                </div>

                                <button

                                    className="btn btn-primary w-100"

                                    disabled={loading}

                                >

                                    {

                                        loading

                                        ?

                                        "Please Wait..."

                                        :

                                        "Login"

                                    }

                                </button>

                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default Login;