package edu.miu.cs489.eshopper.controller;

import edu.miu.cs489.eshopper.model.response.OrderDTO;
import edu.miu.cs489.eshopper.service.interfaces.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }

    @GetMapping("/admin/users/{email}/orders/{orderId}/status/{status}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable String email, @PathVariable Long orderId, @PathVariable String status) {
        return new ResponseEntity<>(orderService.updateOrder(email, orderId, status), HttpStatus.OK);
    }

    @GetMapping("/admin/users/{email}/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable String email, @PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.getOrder(email, orderId), HttpStatus.OK);
    }

    @GetMapping("/admin/users/{email}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable String email) {
        return new ResponseEntity<>(orderService.getOrdersByUserId(email), HttpStatus.OK);
    }


}
