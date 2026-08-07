import { useParams } from "react-router-dom";

function ProductList() {

    const { category } = useParams();

    return (

        <div className="container py-5">

            <h2 className="mb-4 text-center">

                {category.toUpperCase()} Products

            </h2>

            <div className="row">

                {[1,2,3,4,5,6].map((item)=>(
                    <div
                        className="col-lg-4 mb-4"
                        key={item}
                    >

                        <div className="card shadow-sm h-100">

                            <img
                                src="https://picsum.photos/400/300"
                                className="card-img-top"
                                alt="Product"
                            />

                            <div className="card-body">

                                <h5>

                                    {category} Product {item}

                                </h5>

                                <h6 className="text-success">

                                    ₹{item*1200}

                                </h6>

                                <button
                                    className="btn btn-primary w-100 mt-3"
                                >

                                    View Details

                                </button>

                            </div>

                        </div>

                    </div>
                ))}

            </div>

        </div>

    );

}

export default ProductList;