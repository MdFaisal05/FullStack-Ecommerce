import api from "../api/api";

const login = (data) => {
    return api.post("/auth/login", data);
};

const register = (data) => {
    return api.post("/auth/register", data);
};

const getCurrentUser = () => {

    return api.get("/auth/me");

};

export default {

    register,

    login,

    getCurrentUser

};