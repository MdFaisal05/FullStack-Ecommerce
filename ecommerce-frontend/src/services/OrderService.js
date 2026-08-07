import api from "./api";

const placeOrder = (data) => {
    return api.post("/orders", data);
};

const getMyOrders = () => {
    return api.get("/orders/my-orders");
};

const getOrderById = (id) => {
    return api.get(`/orders/${id}`);
};

const cancelOrder = (id) => {
    return api.put(`/orders/${id}/cancel`);
};

const downloadInvoice = (id) => {
    return api.get(`/orders/${id}/invoice`, {
        responseType: "blob"
    });
};

const trackOrder = (id) => {
    return api.get(`/orders/${id}/track`);
};

export default {
    placeOrder,
    getMyOrders,
    getOrderById,
    cancelOrder,
    downloadInvoice,
    trackOrder
};