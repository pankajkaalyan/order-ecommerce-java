package com.order.ecommerce.controller;


import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.dto.ResponseDto;
import com.order.ecommerce.exception.CustomValidationException;
import com.order.ecommerce.service.IAuthenticationService;
import com.order.ecommerce.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final IOrderService orderService;
    private final IAuthenticationService authenticationService;

    /**
     * Creates order
     * @param orderDto
     * @return
     */
    @PostMapping
    @Operation(summary = "Create an order", description = "Create an order")
    public OrderResponseDto createOrder(@RequestBody OrderDto orderDto,
                                        @RequestParam("token") String token) {
        authenticationService.verifyTokenAndUserStatus(token);
        validateArgument(orderDto);
        return orderService.createOrder(orderDto);
    }

    /**
     * Finds Order by Id
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    @Operation(summary = "Find order", description = "Find order by id")
    public OrderDto findOrderBy(@PathVariable(name = "orderId") String orderId,
                                @RequestParam("token") String token) {
        authenticationService.verifyTokenAndUserStatus(token);
        validateArgument(orderId == null || orderId.isEmpty(), "order id cannot be null or empty");
        return orderService.findOrderById(orderId);
    }

    /**
     * Updates order status
     * @param orderId
     * @param orderStatus
     */
    @PatchMapping("/{orderId}")
    @Operation(summary = "Update order status", description = "Update order status")
    public ResponseDto updateOrderStatus(@PathVariable("orderId") String orderId,
                                         @RequestParam(name = "orderStatus") String orderStatus,
                                         @RequestParam("token") String token) {
        authenticationService.verifyTokenAndUserStatus(token);
        validateArgument(orderId == null || orderId.isEmpty(), "order id cannot be null or empty");
        validateArgument(orderStatus == null || orderStatus.isEmpty(), "order status cannot be null or empty");
        return orderService.updateOrderStatus(orderId, orderStatus);
    }

    private void validateArgument(OrderDto orderDto) {
        validateArgument(orderDto.getCustomerId() == null || orderDto.getCustomerId().isEmpty(), "customer id cannot be null or empty.");
        validateArgument(orderDto.getSubTotal() < 0, "subtotal cannot be negative.");
        validateArgument(orderDto.getTax() < 0, "tax amount cannot be negative.");
        validateArgument(orderDto.getShippingCharges() < 0, "shipping charge cannot be negative.");
        validateArgument(orderDto.getTotalAmt() < 0 , "total amount cannot be negative.");
        validateArgument(orderDto.getTotalAmt() != orderDto.getSubTotal() + orderDto.getShippingCharges() + orderDto.getTax() , "total amount is incorrect");
        validateArgument(orderDto.getPaymentMode() == null || orderDto.getPaymentMode().toString().isEmpty(), "payment mode cannot be null or empty");
        validateArgument(orderDto.getShippingMode() == null || orderDto.getShippingMode().toString().isEmpty(), "shipping mode cannot be null or empty");
        validateArgument(orderDto.getBillingAddress() == null, "billing address cannot be null");
        validateArgument(orderDto.getShippingAddress() == null, "shipping address cannot be null");
        validateArgument(orderDto.getTitle() == null || orderDto.getTitle().isEmpty(), "title cannot be null or empty.");
        validateArgument(orderDto.getOrderItems() == null || orderDto.getOrderItems().isEmpty(), "order items cannot be null or empty");
    }

    private void validateArgument(boolean condition, String message) {
        if (condition) {
            log.error("Error while processing request with message = {}", message);
            throw new CustomValidationException("Error in validating input paramters :: " + message);
        }
    }

}
