import "./Footer.css";

import {
    FaFacebook,
    FaInstagram,
    FaYoutube,
    FaXTwitter
} from "react-icons/fa6";

function Footer() {

    return (

      <footer
          className="footer mt-5"
          style={{
              backgroundColor: "#212121",
              fontSize: "14px"
          }}
      >

            <div className="container-fluid px-5 py-5">

                <div className="row">

                    {/* ABOUT */}

                    <div className="col-lg-2 col-md-6 mb-4">

                        <h6 className="text-secondary text-uppercase mb-3">

                            About

                        </h6>

                        <ul className="list-unstyled">

                            <li className="mb-2">Contact Us</li>

                            <li className="mb-2">About Us</li>

                            <li className="mb-2">Careers</li>

                            <li className="mb-2">Blog</li>

                            <li className="mb-2">Press</li>

                        </ul>

                    </div>

                    {/* COMPANY */}

                    <div className="col-lg-2 col-md-6 mb-4">

                        <h6 className="text-secondary text-uppercase mb-3">

                            Company

                        </h6>

                        <ul className="list-unstyled">

                            <li className="mb-2">Products</li>

                            <li className="mb-2">Categories</li>

                            <li className="mb-2">Offers</li>

                            <li className="mb-2">Gift Cards</li>

                        </ul>

                    </div>

                    {/* HELP */}

                    <div className="col-lg-2 col-md-6 mb-4">

                        <h6 className="text-secondary text-uppercase mb-3">

                            Help

                        </h6>

                        <ul className="list-unstyled">

                            <li className="mb-2">Payments</li>

                            <li className="mb-2">Shipping</li>

                            <li className="mb-2">Cancellation</li>

                            <li className="mb-2">Returns</li>

                            <li className="mb-2">FAQ</li>

                        </ul>

                    </div>

                    {/* POLICY */}

                    <div className="col-lg-2 col-md-6 mb-4">

                        <h6 className="text-secondary text-uppercase mb-3">

                            Policy

                        </h6>

                        <ul className="list-unstyled">

                            <li className="mb-2">

                                Privacy Policy

                            </li>

                            <li className="mb-2">

                                Terms & Conditions

                            </li>

                            <li className="mb-2">

                                Refund Policy

                            </li>

                            <li className="mb-2">

                                Security

                            </li>

                        </ul>

                    </div>

                    {/* CONTACT */}

                    <div
                        className="col-lg-2 col-md-6 mb-4"
                        style={{
                            borderLeft: "1px solid #555"
                        }}
                    >

                        <h6 className="text-secondary text-uppercase mb-3">

                            Mail Us

                        </h6>

                        <p className="mb-1">

                           LazyMarket Pvt Ltd

                        </p>

                        <p className="mb-1">

                           UttarPradesh

                        </p>

                        <p className="mb-1">

                          Varanasi

                        </p>

                        <p className="mb-1">

                            India

                        </p>

                    </div>

                    {/* OFFICE */}

                    <div className="col-lg-2 col-md-6">

                        <h6 className="text-secondary text-uppercase mb-3">

                            Office

                        </h6>

                        <p>

                           LazyMarket Pvt Ltd

                            <br />

                            Outer Ring Road

                            <br />

                           UttarPradesh

                            <br />

                           Varanasi

                            <br />

                            PIN : 221001

                        </p>

                    </div>

                </div>

                <hr className="border-secondary mt-4" />

                {/* Social Section */}

             <div className="d-flex align-items-center gap-4 social-icons">

                 <span className="text-secondary">

                     Follow Us

                 </span>

                 <FaFacebook size={22}/>

                 <FaInstagram size={22}/>

                 <FaYoutube size={22}/>

                 <FaXTwitter size={22}/>

             </div>

{/* Bottom Footer */}

<hr className="border-secondary my-4" />

<div className="row align-items-center footer-bottom">

    <div className="col-lg-7">

        <div className="d-flex flex-wrap gap-4 justify-content-center justify-content-lg-start">

            <span>🛍 Become Seller</span>

            <span>📢 Advertise</span>

            <span>🎁 Gift Cards</span>

            <span>❓ Help Center</span>

        </div>

    </div>

    <div className="col-lg-3 text-center mt-3 mt-lg-0">

        © 2026 LazyMarket.com

    </div>

       <div className="col-lg-2 mt-3 mt-lg-0">

           <div className="d-flex flex-wrap gap-2 justify-content-center">

               <div className="payment-badge">
                   VISA
               </div>

               <div className="payment-badge">
                   MASTER
               </div>

               <div className="payment-badge">
                   RuPay
               </div>

               <div className="payment-badge">
                   UPI
               </div>

           </div>

       </div>

   </div>

   </div>

   </footer>

       );

   }

   export default Footer;