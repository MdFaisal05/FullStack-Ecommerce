import axios from "axios";

const BASE_URL = "http://localhost:8080/api/categories";

const getAllCategories = () => {

    return axios.get(BASE_URL);

};

export default {

    getAllCategories

};