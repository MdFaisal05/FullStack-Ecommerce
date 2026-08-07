import { useEffect, useState } from "react";

import { Link } from "react-router-dom";

import productService from "../services/productService";

import "./FeaturedProducts.css";

function FeaturedProducts() {

    const [products, setProducts] = useState([]);

    useEffect(() => {

        loadProducts();

    }, []);

    const loadProducts = async () => {

        try{

            const response =
                await productService.getAllProducts();

            setProducts(response.data);

        }

        catch(error){

            console.log(error);

        }

    };

    return(

        <div className="container mt-5">

            <h2 className="fw-bold mb-4">

                Featured Products

            </h2>

            <div className="row">

                {

                    products.slice(0,8).map(product=>(

                        <div
                            className="col-lg-3 col-md-4 col-sm-6 mb-4"
                            key={product.id}
                        >

                            <div className="card product-card h-100">

                                <img

                                    src={
                                        product.imageUrl ||
                                        "https://via.placeholder.com/300x220?text=No+Image"
                                    }

                                    className="card-img-top product-image"

                                    alt={product.name}

                                />

                                <div className="card-body">

                                    <h6 className="product-title">

                                        {product.name}

                                    </h6>

                                    <div className="product-price">

                                        ₹ {product.price}

                                    </div>

                                    <div className="discount">

                                        In Stock : {product.stock}

                                    </div>

                                    <Link

                                        to={"/products/"+product.id}

                                        className="btn btn-primary w-100 mt-3"

                                    >

                                        View Details

                                    </Link>

                                </div>

                            </div>

                        </div>

                    ))

                }

            </div>

        </div>

    );

}

export default FeaturedProducts;