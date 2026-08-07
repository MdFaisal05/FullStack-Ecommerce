import { useEffect, useState } from "react";

import "./Categories.css";

import categoryService from "../services/categoryService";

function Categories() {

    const [categories, setCategories] = useState([]);

    useEffect(() => {

        loadCategories();

    }, []);

    const loadCategories = async () => {

        try {

            const response =
                await categoryService.getAllCategories();

            setCategories(response.data);

        }

        catch (error) {

            console.log(error);

        }

    };

    return (

        <div className="container mt-5">

            <h2 className="fw-bold mb-4">

                Shop By Categories

            </h2>

            <div className="row">

                {

                    categories.map((category) => (

                        <div
                            key={category.id}
                            className="col-lg-3 col-md-4 col-sm-6 mb-4"
                        >

                            <div className="card category-card h-100 shadow-sm">

                                <div className="card-body text-center">

                                    <h4>

                                        📦

                                    </h4>

                                    <h5>

                                        {category.name}

                                    </h5>

                                </div>

                            </div>

                        </div>

                    ))

                }

            </div>

        </div>

    );

}

export default Categories;