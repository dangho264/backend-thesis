package com.thesis.orderservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    public StripeService() {
        Stripe.apiKey = "sk_test_51OIoMuAm515L3mYRQJAUL4aF90e6UvDZ0uWRQOYMaNEb98AZLpPtncNQChu6bkOJU7aSgLiHO7uX9O1DZHHFrgfc00r16lyZjj";  // Sử dụng secretKey từ application.properties
        System.out.println("Stripe API Key: " + secretKey);
    }
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    public Session createCheckoutSession() throws StripeException {
        Session session = Session.create(createCheckoutSessionParams());

        // Remove StripeResponse from Session to avoid serialization issues
        session.setLastResponse(null);

        return session;
    }
    public String createProductAndPrice(String productName, long price) throws StripeException {
        // Set API key
        Stripe.apiKey = secretKey;

        // Tạo sản phẩm
        Product product = Product.create(ProductCreateParams.builder()
                .setName(productName)
                .setType(ProductCreateParams.Type.SERVICE)
                .build());

        // Tạo giá cho sản phẩm
        Price stripePrice = Price.create(PriceCreateParams.builder()
                .setProduct(product.getId())
                .setCurrency("vnd")  // Đổi loại tiền tệ nếu cần
                .setUnitAmount(price)
                .setRecurring(PriceCreateParams.Recurring.builder()
                        .build())
                .build());

        // Trả về ID của giá vừa tạo
        return stripePrice.getId();
    }
    private SessionCreateParams createCheckoutSessionParams() {
        return SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/success")
                .setCancelUrl("http://localhost:3000/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(2L)
                                .setPrice("price_1OIowCAm515L3mYRIGTGMWxD")
                                .build()
                )
                .build();
    }
    // Các phương thức xử lý thanh toán ở đây
}
