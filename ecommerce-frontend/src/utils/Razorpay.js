import PaymentService from "../services/PaymentService";

export const openRazorpay = async (amount) => {

    const response =
        await PaymentService.createRazorpayOrder(amount);

    const order = response.data;

    const options = {

        key: order.razorpayKey,

        amount: order.amount,

        currency: order.currency,

        name: "Ecommerce Store",

        description: "Order Payment",

        order_id: order.orderId,

        handler: async function (payment) {

            await PaymentService.verifyPayment({

                razorpayOrderId:
                    payment.razorpay_order_id,

                razorpayPaymentId:
                    payment.razorpay_payment_id,

                razorpaySignature:
                    payment.razorpay_signature

            });

            window.location.href =
                "/payment-success";

        },

        theme: {

            color: "#0d6efd"

        }

    };

    const razorpay =
        new window.Razorpay(options);

    razorpay.open();

};