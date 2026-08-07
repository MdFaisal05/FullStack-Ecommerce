import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import HeroSection from "../components/HeroSection";
import CategorySection from "../components/CategorySection";

import productService from "../services/productService";

function Home() {

    const [products, setProducts] = useState([]);

    useEffect(() => {

        loadProducts();

    }, []);

const loadProducts = async () => {

    try {

        const response = await productService.getAllProducts();

        setProducts(response.data);

    }

    catch (error) {

        console.log(error);

    }

};

    return (

        <>

            {/* Hero Section */}

            <section className="bg-dark text-white py-5">

                <div className="container">

                    <div className="row align-items-center">

                        <div className="col-lg-6">

                            <h1 className="display-4 fw-bold">

                                Welcome To LazyMarket

                            </h1>

                            <p className="lead mt-3">

                                Buy the latest electronics, fashion,
                                mobiles and much more at the best prices.

                            </p>

                            <Link
                                to="/products"
                                className="btn btn-warning btn-lg mt-3"
                            >
                                Shop Now
                            </Link>

                        </div>

                        <div className="col-lg-6 text-center">

                            <img
                                src="https://images.unsplash.com/photo-1512436991641-6745cdb1723f?w=800"
                                className="img-fluid rounded shadow"
                                alt="Shopping"
                            />

                        </div>

                    </div>

                </div>

            </section>

            {/* Categories */}

           <section className="container py-5">

               <h2 className="text-center mb-5">

                   Shop By Category

               </h2>

               <div className="row justify-content-center g-4">

                   <div className="col-lg-3 col-md-6">

                       <Link
                           to="/category/Mobiles"
                           className="text-decoration-none text-dark"
                       >
                           <div className="card text-center shadow h-100">

                               <div className="card-body">

                                   <h4>Mobiles</h4>

                               </div>

                           </div>
                       </Link>

                   </div>

                   <div className="col-lg-3 col-md-6">

                       <Link
                           to="/category/Laptops"
                           className="text-decoration-none text-dark"
                       >
                           <div className="card text-center shadow h-100">

                               <div className="card-body">

                                   <h4>Laptops</h4>

                               </div>

                           </div>
                       </Link>

                   </div>

                   <div className="col-lg-3 col-md-6">

                       <Link
                           to="/category/Fashion"
                           className="text-decoration-none text-dark"
                       >
                           <div className="card text-center shadow h-100">

                               <div className="card-body">

                                   <h4>Fashion</h4>

                               </div>

                           </div>
                       </Link>

                   </div>

                   <div className="col-lg-3 col-md-6">

                       <Link
                           to="/category/Accessories"
                           className="text-decoration-none text-dark"
                       >
                           <div className="card text-center shadow h-100">

                               <div className="card-body">

                                   <h4>Accessories</h4>

                               </div>

                           </div>
                       </Link>

                   </div>

               </div>

           </section>

            {/* Latest Products */}

           <section className="container pb-5">

               <h2 className="text-center mb-4">

                   Latest Products

               </h2>

               <div className="row">

                   {

                       products.map((product) => (

                           <div
                               className="col-lg-4 mb-4"
                               key={product.id}
                           >

                               <div className="card h-100 shadow-sm">

                                   <img
                                       src={
                                           product.imageUrl ||
                                           "https://via.placeholder.com/400x300"
                                       }
                                       className="card-img-top"
                                       alt={product.name}
                                       style={{
                                           height: "220px",
                                           objectFit: "cover"
                                       }}
                                   />

                                  <div className="card-body">

                                      <h5>{product.name}</h5>

                                      <h6 className="text-success">

                                          ₹{product.price}

                                      </h6>

                                      <Link
                                          to={`/product/${product.id}`}
                                          className="btn btn-primary w-100"
                                      >
                                          View Product
                                      </Link>

                                  </div>

                               </div>

                           </div>

                       ))

                   }

               </div>

           </section>

        </>

    );


}

export default Home;