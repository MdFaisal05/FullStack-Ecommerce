export const saveToken = (token) => {
    localStorage.setItem("token", token);
};

export const getToken = () => {
    return localStorage.getItem("token");
};

export const isLoggedIn = () => {
    return getToken() !== null;
};

export const logout = () => {
    localStorage.removeItem("token");
};

export const getAuthHeader = () => {
    return {
        Authorization: `Bearer ${getToken()}`
    };
};