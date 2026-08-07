function HeroSection() {
    return (
        <div className="container-fluid mt-3">

            <div
                className="bg-primary text-white text-center rounded shadow p-5"
                style={{ minHeight: "400px" }}
            >

                <h1 className="display-4 fw-bold">
                    Welcome to LazyMarket
                </h1>

                <p className="lead mt-3">
                    Buy Electronics, Mobiles, Fashion and Accessories at Best Prices.
                </p>

                <button className="btn btn-warning btn-lg mt-3">
                    Shop Now
                </button>

            </div>

        </div>
    );
}

export default HeroSection;