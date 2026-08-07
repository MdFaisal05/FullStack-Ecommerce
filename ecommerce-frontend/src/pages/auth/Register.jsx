import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import toast from "react-hot-toast";
import authService from "../../services/authService";

function Register() {

    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);

const [form, setForm] = useState({

    firstName: "",

    lastName: "",

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

            const response = await authService.register(form);

            toast.success(response.data);

            navigate("/login");

        }

        catch (error) {

            toast.error(

                error.response?.data?.message ||

                error.response?.data ||

                "Registration Failed"

            );

        }

        finally {

            setLoading(false);

        }

    };

    return (

        <div className="container mt-5">

            <div className="row justify-content-center">

                <div className="col-md-6">

                    <div className="card shadow">

                        <div className="card-body">

                            <h3 className="text-center mb-4">

                                Create Account

                            </h3>

                            <form onSubmit={handleSubmit}>
<div className="mb-3">

    <label>First Name</label>

    <input

        type="text"

        className="form-control"

        name="firstName"

        value={form.firstName}

        onChange={handleChange}

        required

    />

</div>

<div className="mb-3">

    <label>Last Name</label>

    <input

        type="text"

        className="form-control"

        name="lastName"

        value={form.lastName}

        onChange={handleChange}

        required

    />

</div>

                                <div className="mb-3">

                                    <label>Email</label>

                                    <input
                                        type="email"
                                        className="form-control"
                                        name="email"
                                        value={form.email}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>


                                <div className="mb-3">

                                    <label>Password</label>

                                    <input
                                        type="password"
                                        className="form-control"
                                        name="password"
                                        value={form.password}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <button
                                    className="btn btn-success w-100"
                                    disabled={loading}
                                >

                                    {
                                        loading
                                            ? "Please Wait..."
                                            : "Register"
                                    }

                                </button>

                            </form>

                            <hr />

                            <p className="text-center">

                                Already have an account?

                                <Link
                                    to="/login"
                                    className="ms-2"
                                >

                                    Login

                                </Link>

                            </p>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default Register;