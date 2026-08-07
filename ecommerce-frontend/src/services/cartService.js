import axios from "axios";

const API = "http://localhost:8080/api/cart";

// Get JWT Token
const getToken = () => {

    return localStorage.getItem("token");

};

// Authorization Header
const authHeader = () => {

    return {

        headers: {

            Authorization: `Bearer ${getToken()}`

        }

    };

};

// ==============================
// GET CART
// ==============================

const getCart = () => {

    return axios.get(

        API,

        authHeader()

    );

};

// ==============================
// ADD TO CART
// ==============================

const addToCart = (productId, quantity = 1) => {

    return axios.post(

        `${API}/add`,

        {

            productId,

            quantity

        },

        authHeader()

    );

};

// ==============================
// UPDATE CART ITEM
// ==============================

const updateQuantity = (

    cartItemId,

    quantity

) => {

    return axios.put(

        `${API}/update/${cartItemId}`,

        {

            quantity

        },

        authHeader()

    );

};

// ==============================
// REMOVE ITEM
// ==============================

const removeItem = (cartItemId) => {

    return axios.delete(

        `${API}/remove/${cartItemId}`,

        authHeader()

    );

};

// ==============================
// CLEAR CART
// ==============================

const clearCart = () => {

    return axios.delete(

        `${API}/clear`,

        authHeader()

    );

};

const cartService = {

    getCart,

    addToCart,

    updateQuantity,

    removeItem,

    clearCart

};

export default cartService;