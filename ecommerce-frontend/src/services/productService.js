import axios from "axios";

const API = "http://localhost:8080/api/products";

const getAllProducts = () => {

    return axios.get(API);

};

const getProductById = (id) => {

    return axios.get(`${API}/${id}`);

};

const productService = {

    getAllProducts,

    getProductById

};

export default productService;