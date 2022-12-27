package com.order.ecommerce.dto;

import com.order.ecommerce.enums.PaymentMode;
import com.order.ecommerce.enums.ShippingMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderDto {
    private final String customerId;
    private double subTotal;
    private double totalAmt;
    private double tax;
    private double shippingCharges;
    private ShippingMode shippingMode;
    private PaymentMode paymentMode;
    private AddressDto billingAddress;
    private AddressDto shippingAddress;
    private String title;
    private final List<OrderItemDto> orderItems;
}
