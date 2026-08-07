import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import productService from "../services/productService";

function ProductDetails() {

    const { id } = useParams();

    const [product, setProduct] = useState(null);

    useEffect(() => {

        loadProduct();

    }, []);

useEffect(() => {

    loadProduct();

}, [id]);

    const loadProduct = async () => {

        try {

            const response = await productService.getProductById(id);

            setProduct(response.data);

        }

        catch (error) {

            console.log(error);

        }

    };

    if (!product) {

        return (

            <div className="container mt-5 text-center">

                <h3>Loading...</h3>

            </div>

        );

    }

    return (

        <div className="container mt-5">

            <div className="row">

                <div className="col-md-6">

                    <img

                        src={
                            product.imageUrl ||
                            "https://via.placeholder.com/600x500"
                        }

                        alt={product.name}

                        className="img-fluid rounded shadow"

                        onError={(e) => {
                            e.target.src =
                                "https://via.placeholder.com/600x500";
                        }}

                    />

                </div>

                <div className="col-md-6">

                    <h2>

                        {product.name}

                    </h2>

                    <h3 className="text-success mt-3">

                        ₹{product.price}

                    </h3>

                    <p className="mt-4">

                        {product.description}

                    </p>

                    <button className="btn btn-success btn-lg">

                        Add To Cart

                    </button>

                </div>

            </div>

        </div>

    );

}

export default ProductDetails;